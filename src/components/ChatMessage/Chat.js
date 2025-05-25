import React, { useContext, useEffect, useState } from "react";
import { Container, Spinner, ListGroup } from "react-bootstrap";
import { db } from "../../configs/FireBase";

import {
  collection,
  query,
  where,
  getDocs,
  getDoc,
  doc,
  onSnapshot,
  orderBy,
  limit,
} from "firebase/firestore";
import { MyUserContext } from "../../configs/MyContexts";
import ChatList from "./ChatList";

const Chat = () => {
  const userContext = useContext(MyUserContext);
  const [loading, setLoading] = useState(true);
  const [usersChatWithMe, setUsersChatWithMe] = useState([]);
  let userId = "";
  if (userContext) {
    userId = userContext.id;
  }

  useEffect(() => {
    if (!userId) return;

    const q = query(
      collection(db, "conversations"),
      where("participants", "array-contains", userId)
    );

    const unsubscribeConversations = onSnapshot(q, (querySnapshot) => {
      const tempMap = new Map();
      const unsubscribes = [];

      querySnapshot.forEach((docSnapshot) => {
        const data = docSnapshot.data();
        const conversationId = docSnapshot.id;
        const otherUserId = data.participants.find((id) => id !== userId);

        if (otherUserId) {
          const userDocRef = doc(db, "users", otherUserId.toString());
          const lastMsgQuery = query(
            collection(db, "conversations", conversationId, "messages"),
            orderBy("createdAt", "desc"),
            limit(1)
          );

          const unsubscribeMsg = onSnapshot(lastMsgQuery, async (snapshot) => {
            let lastMessage = null;
            if (!snapshot.empty) {
              lastMessage = snapshot.docs[0].data();
            }

            const userDoc = await getDoc(userDocRef);
            if (userDoc.exists()) {
              const userData = {
                id: otherUserId,
                ...userDoc.data(),
                lastMessage,
              };

              tempMap.set(otherUserId, userData);

              // Chỉ cập nhật state khi đủ dữ liệu từ tất cả participants
              if (tempMap.size === querySnapshot.size) {
                setUsersChatWithMe(Array.from(tempMap.values()));
                setLoading(false);
              }
            }
          });

          unsubscribes.push(unsubscribeMsg);
        }
      });

      return () => {
        unsubscribes.forEach((unsub) => unsub());
      };
    });

    return () => unsubscribeConversations();
  }, [userId]);

  return (
    <Container className="mt-4">
      {loading ? (
        <div className="d-flex justify-content-center">
          <Spinner animation="border" />
        </div>
      ) : (
        <ChatList usersChatWithMe={usersChatWithMe} />
      )}
    </Container>
  );
};

export default Chat;
