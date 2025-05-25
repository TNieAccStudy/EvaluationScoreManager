import { ListGroup, Image, Row, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import moment from "moment"; // Import moment.js

const ChatList = ({ usersChatWithMe }) => {
  const nav = useNavigate();

  const handleChatClick = (user) => {
    nav(`/chat/${user.username}`);
  };

  return (
    <ListGroup>
      <h1 className="text-center text-success mt-3">
        Danh sách người dùng đã nhắn tin với tôi
      </h1>
      {usersChatWithMe.map((user) => (
        <ListGroup.Item
          key={user.id}
          className="d-flex align-items-center w-50"
          action
          onClick={() => handleChatClick(user)}
        >
          <Image
            src={user.avatar}
            roundedCircle
            width={50}
            height={50}
            className="me-3"
            alt={`${user.username}'s avatar`}
          />
          <Row className="flex-grow-1 ">
            <Col xs={8}>
              <strong>{user.username}</strong>
            </Col>
            {user.lastMessage ? (
              <>
                <Col xs={4} className="text-muted">
                  {moment(user.lastMessage.createdAt.toDate()).fromNow()}
                </Col>
                <Col xs={12} className="text-muted">
                  {user.lastMessage.content}
                </Col>
              </>
            ) : (
              <Col xs={12} className="text-muted fst-italic">
                No message yet.
              </Col>
            )}
          </Row>
        </ListGroup.Item>
      ))}
    </ListGroup>
  );
};

export default ChatList;
