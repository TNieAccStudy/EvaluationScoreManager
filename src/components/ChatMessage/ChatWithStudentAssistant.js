import React, {
  useContext,
  useEffect,
  useState,
  useRef,
  useMemo,
  useCallback,
} from "react";
import { Container, Row, Col, Card, Image } from "react-bootstrap";
import moment from "moment";
import {
  collection,
  addDoc,
  query,
  orderBy,
  onSnapshot,
  serverTimestamp,
  doc,
  getDoc,
  setDoc,
} from "firebase/firestore";
import { db } from "../../configs/FireBase";
import { MyUserContext } from "../../configs/MyContexts";
import ChatInput from "./ChatInput";
import { authApis, endpoints } from "../../configs/Apis";

const ChatWithStudentAssistant = () => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState("");
  const currentUser = useContext(MyUserContext);
  const [assistant, setAssistant] = useState(null);
  const currentUserId = currentUser?.id || "";
  const messageEndRef = useRef(null);

  const loadAssistant = async () => {
    const res = await authApis().get(endpoints["assistants"]);
    setAssistant(res.data[0]);
  };
  useEffect(() => {
    loadAssistant();
  }, []);

  const conversationId = useMemo(() => {
    if (!assistant) return null;
    const getConversationId = (user1, user2) => [user1, user2].sort().join("_");
    return getConversationId(currentUserId, assistant.id);
  }, [currentUserId, assistant]);

  const saveUserInfo = useCallback(async (user) => {
    console.log("Saving user info:", user);
    const userRef = doc(db, "users", user.id.toString());
    const userDoc = await getDoc(userRef);

    if (!userDoc.exists()) {
      await setDoc(userRef, {
        id: user.id,
        username: user.username,
        avatar: user.avatar,
        createdAt: serverTimestamp(),
      });
    }
  }, []);

  const createConversationIfNotExists = useCallback(async () => {
    const conversationRef = doc(db, "conversations", conversationId);
    const conversationDoc = await getDoc(conversationRef);

    if (!conversationDoc.exists()) {
      await setDoc(conversationRef, {
        participants: [currentUserId, assistant.id],
        createdAt: serverTimestamp(),
      });
    }

    await saveUserInfo(currentUser);
    await saveUserInfo(assistant);
  }, [conversationId, currentUser, currentUserId, assistant, saveUserInfo]);

  const saveChat = useCallback(
    async (content) => {
      if (!content.trim() || !assistant) return;

      try {
        await createConversationIfNotExists();
        await addDoc(
          collection(db, "conversations", conversationId, "messages"),
          {
            userId: currentUserId,
            content,
            createdAt: serverTimestamp(),
            username: currentUser.username,
            avatar: currentUser.avatar,
          }
        );
        setInputMessage("");
      } catch (error) {
        console.error("Error saving chat: ", error);
      }
    },
    [conversationId, currentUserId, currentUser, createConversationIfNotExists]
  );

  useEffect(() => {
    if (!conversationId) return;
    const q = query(
      collection(db, "conversations", conversationId, "messages"),
      orderBy("createdAt", "asc")
    );

    const unsubscribe = onSnapshot(q, (querySnapshot) => {
      const chats = querySnapshot.docs.map((doc) => ({
        id: doc.id,
        ...doc.data(),
      }));
      setMessages(chats);
    });

    return () => unsubscribe();
  }, [conversationId]);

  // Scroll when messages update
  useEffect(() => {
    if (messageEndRef.current) {
      messageEndRef.current.scrollIntoView({ behavior: "smooth" });
    }
  }, [messages]);

  return assistant ? (
    <Container className="mt-4" style={{ maxWidth: "600px" }}>
      <Row className="align-items-center mb-3">
        <Col xs="auto">
          <Image
            src={assistant.avatar}
            roundedCircle
            style={{ width: "50px", height: "50px" }}
          />
        </Col>
        <Col>
          <h5 className="mb-0">{assistant.username}</h5>
          <small className="text-muted">Online</small>
        </Col>
      </Row>

      <Card
        style={{ borderRadius: "20px", boxShadow: "0 0 10px rgba(0,0,0,0.1)" }}
      >
        <Card.Body
          style={{
            height: "500px",
            overflowY: "auto",
            padding: "15px",
            backgroundColor: "#e5ddd5",
            borderTopLeftRadius: "20px",
            borderTopRightRadius: "20px",
          }}
        >
          {messages.map((message) => {
            const isCurrentUser = message.userId === currentUserId;
            return (
              <div
                key={message.id}
                className={`d-flex ${
                  isCurrentUser
                    ? "justify-content-end"
                    : "justify-content-start"
                } mb-3`}
              >
                {!isCurrentUser && (
                  <Image
                    src={message.avatar}
                    roundedCircle
                    style={{
                      width: "36px",
                      height: "36px",
                      marginRight: "8px",
                    }}
                  />
                )}
                <div style={{ maxWidth: "70%" }}>
                  {!isCurrentUser && (
                    <small className="text-muted d-block mb-1">
                      {message.username}
                    </small>
                  )}
                  <div
                    style={{
                      backgroundColor: isCurrentUser ? "#0b93f6" : "#fff",
                      color: isCurrentUser ? "#fff" : "#000",
                      padding: "10px 14px",
                      borderRadius: "18px",
                      borderTopLeftRadius: isCurrentUser ? "18px" : "0",
                      borderTopRightRadius: isCurrentUser ? "0" : "18px",
                      boxShadow: "0 1px 3px rgba(0,0,0,0.1)",
                      wordWrap: "break-word",
                    }}
                  >
                    {message.content}
                  </div>
                  <small
                    className="text-muted d-block mt-1 text-end"
                    style={{ fontSize: "11px" }}
                  >
                    {message.createdAt
                      ? moment(message.createdAt.toDate()).fromNow()
                      : "Sending..."}
                  </small>
                </div>
              </div>
            );
          })}
          <div ref={messageEndRef} />
        </Card.Body>
        <Card.Footer
          style={{
            backgroundColor: "#f5f5f5",
            borderBottomLeftRadius: "20px",
            borderBottomRightRadius: "20px",
          }}
        >
          <ChatInput
            inputMessage={inputMessage}
            setInputMessage={setInputMessage}
            onSend={saveChat}
          />
        </Card.Footer>
      </Card>
    </Container>
  ) : (
    <Container className="mt-4">
      <p>Loading assistant...</p>
    </Container>
  );
};

export default ChatWithStudentAssistant;
