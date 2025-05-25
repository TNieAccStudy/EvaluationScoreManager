import React, { useContext, useEffect, useState, useRef } from "react";
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
  getDocs,
  where,
} from "firebase/firestore";
import { db } from "../../configs/FireBase";
import { MyUserContext } from "../../configs/MyContexts";
import { useParams } from "react-router-dom";
import ChatInput from "./ChatInput";

const ChatDetail = () => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState("");
  const currentUser = useContext(MyUserContext);
  const currentUserId = currentUser?.id || "";
  const messageEndRef = useRef(null);
  const { username } = useParams();
  const [userReceive, setUserReceive] = useState(null);
  const [conversationId, setConversationId] = useState(null);

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const q = query(
          collection(db, "users"),
          where("username", "==", username)
        );
        const querySnapshot = await getDocs(q);
        if (!querySnapshot.empty) {
          const userInfo = querySnapshot.docs[0].data();
          setUserReceive(userInfo);
          const id = [currentUserId, userInfo.id].sort().join("_");
          setConversationId(id);
        } else {
          console.log("No user found with username:", username);
        }
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    };

    if (username && currentUserId) {
      fetchUserInfo();
    }
  }, [username, currentUserId]);

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
      setTimeout(() => {
        messageEndRef.current?.scrollIntoView({ behavior: "smooth" });
      }, 300);
    });

    return () => unsubscribe();
  }, [conversationId]);

  const createConversationIfNotExists = async () => {
    if (!conversationId || !userReceive) return;
    const conversationRef = doc(db, "conversations", conversationId);
    const conversationDoc = await getDoc(conversationRef);

    if (!conversationDoc.exists()) {
      await setDoc(conversationRef, {
        participants: [currentUserId, userReceive.id],
        createdAt: serverTimestamp(),
      });
    }
  };

  const saveChat = async (content) => {
    if (!content.trim() || !conversationId) return;

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
  };

  return userReceive ? (
    <Container className="mt-4" style={{ maxWidth: "600px" }}>
      <Row className="align-items-center mb-3">
        <Col xs="auto">
          <Image
            src={userReceive.avatar}
            roundedCircle
            style={{ width: "50px", height: "50px" }}
          />
        </Col>
        <Col>
          <h5 className="mb-0">{userReceive.username}</h5>
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
      <p>Loading userReceive...</p>
    </Container>
  );
};

export default ChatDetail;
