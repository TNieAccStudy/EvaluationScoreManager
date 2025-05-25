import { useParams } from "react-router-dom";
import { useCallback, useEffect, useState } from "react";
import {
  Button,
  Card,
  Container,
  Row,
  Col,
  Badge,
  Form,
  Image,
} from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import moment from "moment";

const BulletinDetail = () => {
  const { bulletinId } = useParams();
  const [bulletin, setBulletin] = useState(null);
  const [likes, setLikes] = useState(0);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState([]);
  const [registeredActivityIds, setRegisteredActivityIds] = useState([]);

  const loadActivitiesRegistries = async () => {
    try {
      const res = await authApis().get(
        endpoints["activities-registries-of-student"]
      );
      const ids = res.data.map((item) => item.extraActivityId.id);
      console.log("Registered Activity IDs:", ids);
      setRegisteredActivityIds(ids);
    } catch (err) {
      console.error("Lỗi khi tải danh sách đăng ký hoạt động:", err);
      return [];
    }
  };
  useEffect(() => {
    loadActivitiesRegistries();
  }, []);

  const loadDetail = useCallback(async () => {
    try {
      const res = await authApis().get(
        endpoints["bulletins-detail"](bulletinId)
      );
      setBulletin(res.data);
      setLikes(res.data.likes || 0);
    } catch (err) {
      console.log(err);
      alert("Lỗi khi tải thông tin bản tin");
    }
  }, [bulletinId]);

  const loadComments = useCallback(async () => {
    try {
      const res = await authApis().get(
        endpoints["bulletins-interactions"](bulletinId),
        {
          params: { type: "comment" },
        }
      );
      setComments(res.data);
    } catch (err) {
      console.error("Lỗi khi tải bình luận:", err);
      alert("Không thể tải bình luận. Vui lòng thử lại.");
    }
  }, [bulletinId]);

  useEffect(() => {
    loadDetail();
    loadComments();
  }, [loadDetail, loadComments, bulletinId]);

  const handleRegister = async (activityId, studentId) => {
    const data = {
      extraActivityId: activityId,
      studentId: {
        id: studentId,
        userType: "student",
      },
    };
    try {
      const res = await authApis().post(endpoints["registries"], data, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (res.status === 201) {
        alert("Đăng ký thành công");
        setRegisteredActivityIds((prev) => [...prev, activityId]);
      } else {
        alert("Đăng ký thất bại");
      }
    } catch (err) {
      console.error("Lỗi đăng ký hoạt động:", err);
      alert("Đăng ký thất bại. Vui lòng thử lại.");
    }
  };

  const handleLike = async () => {
    try {
      await authApis().post(endpoints["bulletins-like"](bulletinId));
      setLikes((prev) => prev + 1);
    } catch (err) {
      console.error("Lỗi khi thả tim:", err);
      alert("Không thể thả tim. Vui lòng thử lại.");
    }
  };

  const handleComment = () => {
    if (newComment.trim() !== "") {
      setComments([...comments, newComment]);
      setNewComment("");
    }
  };

  if (!bulletin) return <p>Loading...</p>;

  const {
    title,
    content,
    createdDate,
    duration,
    semesterId,
    bulletinType,
    studentAssistantId,
    extraActivity,
  } = bulletin;

  // Kiểm tra đã đăng ký hay chưa
  const isRegistered =
    extraActivity && registeredActivityIds.includes(extraActivity.id);

  return (
    <Container className="my-4">
      <Row className="justify-content-center">
        <Col md={8}>
          {/* Bulletin Info */}
          <Card
            style={{
              backgroundColor: "#f8f9fa",
              borderRadius: "8px",
              boxShadow: "0 4px 8px rgba(0,0,0,0.1)",
            }}
          >
            <Card.Body>
              <div className="d-flex justify-content-between align-items-start">
                <div>
                  <Card.Title as="h3" style={{ color: "#2c3e50" }}>
                    {title}
                  </Card.Title>
                  <Card.Subtitle
                    className="mb-2 text-muted"
                    style={{ fontSize: "0.9rem" }}
                  >
                    Thời gian diễn ra: <strong>{createdDate}</strong> đến{" "}
                    <strong>{duration}</strong>
                  </Card.Subtitle>
                  <Badge
                    bg="info"
                    className="mb-2"
                    style={{ fontWeight: "600" }}
                  >
                    {semesterId?.name} - {semesterId?.year}
                  </Badge>
                </div>
                <Button
                  variant="outline-danger"
                  onClick={handleLike}
                  className="ms-3"
                  style={{ fontWeight: "bold", fontSize: "1.2rem" }}
                >
                  ❤️ {likes}
                </Button>
              </div>

              <Card.Text
                className="mt-3"
                style={{ color: "#34495e", fontSize: "1.05rem" }}
              >
                {content}
              </Card.Text>
            </Card.Body>
          </Card>

          {/* Extra Activity Info */}
          {bulletinType === "activity" && extraActivity && (
            <Card
              className="mt-3"
              style={{
                backgroundColor: "#e9f7ef",
                borderRadius: "8px",
                border: "1px solid #2ecc71",
              }}
            >
              <Card.Header
                style={{
                  backgroundColor: "#27ae60",
                  color: "white",
                  fontWeight: "700",
                  fontSize: "1.2rem",
                  borderRadius: "8px 8px 0 0",
                }}
              >
                Thông tin hoạt động
              </Card.Header>
              <Card.Body style={{ color: "#2c3e50" }}>
                <h5>{extraActivity.title}</h5>
                <p>{extraActivity.description}</p>
                <p>
                  <strong>Điểm cộng: </strong> {extraActivity.bonusScore}
                </p>
                <p>
                  <strong>Điều khoản: </strong>
                  {extraActivity.termId?.name} (Tối đa{" "}
                  {extraActivity.termId?.maxValue} điểm)
                </p>
                {isRegistered ? (
                  <Button variant="success" disabled>
                    Đã đăng ký
                  </Button>
                ) : (
                  <Button
                    variant="primary"
                    onClick={() =>
                      handleRegister(extraActivity.id, studentAssistantId.id)
                    }
                  >
                    Đăng ký tham gia
                  </Button>
                )}
              </Card.Body>
            </Card>
          )}

          {/* Comments */}
          <Card
            className="mt-4"
            style={{
              backgroundColor: "#f1f2f6",
              borderRadius: "8px",
              boxShadow: "0 2px 6px rgba(0,0,0,0.05)",
            }}
          >
            <Card.Body>
              <h5 style={{ color: "#34495e" }}>Bình luận</h5>

              <Form
                className="mb-3"
                onSubmit={(e) => {
                  e.preventDefault();
                  handleComment();
                }}
              >
                <Form.Control
                  type="text"
                  placeholder="Viết bình luận..."
                  value={newComment}
                  onChange={(e) => setNewComment(e.target.value)}
                />
                <Button variant="success" className="mt-2" type="submit">
                  Gửi
                </Button>
              </Form>

              {comments.length === 0 ? (
                <p className="text-muted fst-italic">Chưa có bình luận nào.</p>
              ) : (
                comments.map((c, idx) => (
                  <Card
                    key={idx}
                    className="mb-3 shadow-sm"
                    style={{
                      backgroundColor: "#f9f9fb",
                      borderRadius: "12px",
                      border: "1px solid #ecf0f1",
                    }}
                  >
                    <Card.Body>
                      <div className="d-flex align-items-center mb-3">
                        <Image
                          src={c.studentId?.avatar}
                          roundedCircle
                          width={40}
                          height={40}
                          className="me-2"
                        />
                        <strong style={{ color: "#0984e3" }}>
                          {c.studentId?.username}
                        </strong>
                        <small className="text-muted m-2">
                          {moment(c.createdDate).fromNow()}
                        </small>
                      </div>
                      <Card.Text
                        className="p-3"
                        style={{
                          backgroundColor: "#ffffff",
                          border: "1px solid #dfe6e9",
                          borderRadius: "8px",
                          color: "#2d3436",
                        }}
                      >
                        {c.content}
                      </Card.Text>
                    </Card.Body>
                  </Card>
                ))
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default BulletinDetail;
