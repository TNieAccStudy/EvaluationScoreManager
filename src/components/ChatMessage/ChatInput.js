import React, { memo } from "react";
import { Form, Row, Col, Button } from "react-bootstrap";

const ChatInput = ({ inputMessage, setInputMessage, onSend }) => {
  const handleSubmit = (e) => {
    e.preventDefault();
    const trimmed = inputMessage.trim();
    if (trimmed) {
      onSend(trimmed);
    }
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Row>
        <Col xs={9}>
          <Form.Control
            type="text"
            placeholder="Type your message..."
            value={inputMessage}
            onChange={(e) => setInputMessage(e.target.value)}
          />
        </Col>
        <Col xs={3}>
          <Button variant="primary" type="submit" className="w-100">
            Send
          </Button>
        </Col>
      </Row>
    </Form>
  );
};

export default memo(ChatInput);
