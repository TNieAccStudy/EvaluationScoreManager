import { useEffect, useState } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import {
  Container,
  Row,
  Col,
  Card,
  Spinner,
  Alert,
  Button,
  Form,
} from "react-bootstrap";

const ActivityRegistries = () => {
  const [activityRegistries, setActivityRegistries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activityAttendanceIds, setActivityAttendanceIds] = useState([]);
  const [showProofFormId, setShowProofFormId] = useState(null);
  const [proofFiles, setProofFiles] = useState({});

  const loadActivityAttendanceIds = async () => {
    try {
      const res = await authApis().get(
        endpoints["activities-attendances-of-student"]
      );
      console.log("Activity attendance IDs:", res.data);
      const confirmedIds = res.data
        .filter((item) => item && item.censorState === "CONFIRMED")
        .map((item) => item.activityRegistryId.id);
      setActivityAttendanceIds(confirmedIds);
    } catch (err) {
      console.error("Error loading activity attendance IDs:", err);
    }
  };

  const loadActivityRegistries = async () => {
    try {
      setLoading(true);
      const res = await authApis().get(
        endpoints["activities-registries-of-student"]
      );
      setActivityRegistries(res.data);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    loadActivityAttendanceIds();
    loadActivityRegistries();
  }, []);

  const handleFileChange = (e, activityId) => {
    setProofFiles((prev) => ({
      ...prev,
      [activityId]: e.target.files[0],
    }));
  };

  const handleProofSubmit = async (activityId) => {
    const formData = new FormData();
    formData.append("proofImage", proofFiles[activityId]);

    try {
      await authApis().post(
        `${endpoints["upload-proof"]}/${activityId}`,
        formData,
        {
          headers: { "Content-Type": "multipart/form-data" },
        }
      );
      alert("Gửi minh chứng thành công!");
      setShowProofFormId(null);
      await loadActivityAttendanceIds();
    } catch (err) {
      alert("Lỗi khi gửi minh chứng!");
    }
  };

  return (
    <Container className="my-4">
      <h1 className="mb-4 text-center">Hoạt động ngoại khóa đã đăng ký</h1>

      {loading && (
        <div className="text-center my-5">
          <Spinner animation="border" role="status" />
          <span className="ms-2">Đang tải dữ liệu...</span>
        </div>
      )}

      {error && (
        <Alert variant="danger" className="text-center">
          Đã xảy ra lỗi: {error.message}
        </Alert>
      )}

      <Row>
        {activityRegistries.map((registry) => {
          const { extraActivityId, studentId } = registry;
          console.log("Registry data:", registry);
          const isAttended = activityAttendanceIds.includes(registry.id);

          return (
            <Col key={registry.id} xs={12} md={6} lg={4} className="mb-4">
              <Card>
                <Card.Header className="d-flex align-items-center">
                  <img
                    src={studentId.avatar}
                    alt="avatar"
                    className="rounded-circle me-3"
                    width="50"
                    height="50"
                    style={{ objectFit: "cover" }}
                  />
                  <div>
                    <div className="fw-bold">
                      {studentId.lastName} {studentId.firstName}
                    </div>
                    <div className="text-muted" style={{ fontSize: "0.9em" }}>
                      MSSV: {studentId.mssv} | Lớp: {studentId.classId.name}
                    </div>
                  </div>
                </Card.Header>
                <Card.Body>
                  <Card.Title>{extraActivityId.title}</Card.Title>
                  <Card.Text>{extraActivityId.description}</Card.Text>
                  <Card.Text className="text-muted">
                    Học kỳ: {extraActivityId.semesterId.name} (
                    {extraActivityId.semesterId.year})<br />
                    Điều khoản: {extraActivityId.termId.name} - tối đa{" "}
                    {extraActivityId.termId.maxValue} điểm
                  </Card.Text>
                  <Card.Text className="text-success fw-bold">
                    +{extraActivityId.bonusScore} điểm
                  </Card.Text>

                  <Card.Text
                    className={isAttended ? "text-primary" : "text-warning"}
                  >
                    Trạng thái:{" "}
                    <strong>
                      {isAttended ? "Đã tham gia" : "Chưa tham gia"}
                    </strong>
                  </Card.Text>

                  {!isAttended && (
                    <>
                      <Button
                        variant="outline-primary"
                        size="sm"
                        onClick={() =>
                          setShowProofFormId(
                            showProofFormId === extraActivityId.id
                              ? null
                              : extraActivityId.id
                          )
                        }
                      >
                        {showProofFormId === extraActivityId.id
                          ? "Đóng minh chứng"
                          : "Minh chứng tham gia"}
                      </Button>

                      {showProofFormId === extraActivityId.id && (
                        <Form className="mt-3">
                          <Form.Group controlId={`file-${extraActivityId.id}`}>
                            <Form.Label>Ảnh minh chứng</Form.Label>
                            <Form.Control
                              type="file"
                              onChange={(e) =>
                                handleFileChange(e, extraActivityId.id)
                              }
                            />
                          </Form.Group>
                          <Button
                            variant="success"
                            className="mt-2"
                            onClick={() =>
                              handleProofSubmit(extraActivityId.id)
                            }
                            disabled={!proofFiles[extraActivityId.id]}
                          >
                            Gửi minh chứng
                          </Button>
                        </Form>
                      )}
                    </>
                  )}
                </Card.Body>
              </Card>
            </Col>
          );
        })}
      </Row>
    </Container>
  );
};

export default ActivityRegistries;
