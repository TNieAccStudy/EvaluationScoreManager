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
  Modal,
} from "react-bootstrap";
import MySpinner from "../layouts/MySpinner";

const ActivityRegistries = () => {
  const [activityRegistries, setActivityRegistries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activityAttendanceIds, setActivityAttendanceIds] = useState([]);
  const [pendingIds, setPendingIds] = useState([]);
  const [canceledIds, setCanceledIds] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [selectedRegistry, setSelectedRegistry] = useState(null);
  const [evidenceImage, setEvidenceImage] = useState(null);

  const loadActivityAttendanceIds = async () => {
    try {
      setLoading(true);
      const res = await authApis().get(
        endpoints["activities-attendances-of-student"]
      );
      console.log("Activity attendance IDs:", res.data);

      const getIdsByState = (state) =>
        res.data
          .filter((item) => item && item.censorState === state)
          .map((item) => item.activityRegistryId.id);

      const confirmedIds = getIdsByState("CONFIRMED");
      const pendingIds = getIdsByState("PENDING");
      const canceledIds = getIdsByState("CANCELED");

      setPendingIds(pendingIds);
      setCanceledIds(canceledIds);
      setActivityAttendanceIds(confirmedIds);
    } catch (err) {
      console.error("Error loading activity attendance IDs:", err);
    } finally {
      setLoading(false);
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

  const handleOpenModal = (registryId) => {
    setSelectedRegistry(registryId);
    setShowModal(true);
  };
  const handleFileChange = (e) => {
    setEvidenceImage(e.target.files[0]);
  };
  const handleCloseModal = () => {
    setShowModal(false);
    setEvidenceImage(null);
    setSelectedRegistry(null);
  };

  const handleSubmitEvidence = async (id) => {
    if (!evidenceImage) return;

    const form = new FormData();
    form.append("proofPicture", evidenceImage);

    const data = JSON.stringify({
      activityRegistryId: id,
    });

    const dataBlob = new Blob([data], { type: "application/json" });
    form.append("data", dataBlob);
    try {
      setLoading(true);
      const res = await authApis().post(endpoints["attendances"], form);
      console.log("Response from server:", res.data);
      setPendingIds((prevPendingIds) => [...prevPendingIds, id]);
      handleCloseModal();
    } catch (err) {
      alert("Lỗi khi gửi minh chứng: " + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container className="my-4">
      <h1 className="mb-4 text-center">Hoạt động ngoại khóa đã đăng ký</h1>

      {loading && <MySpinner />}

      {error && (
        <Alert variant="danger" className="text-center">
          Đã xảy ra lỗi: {error.message}
        </Alert>
      )}

      <Row>
        {activityRegistries.map((registry) => {
          const { extraActivityId, studentId } = registry;
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

                  {!isAttended ? (
                    pendingIds.includes(registry.id) ? (
                      <Button variant="success" size="sm">
                        Đã gửi minh chứng
                      </Button>
                    ) : canceledIds.includes(registry.id) ? (
                      <Button variant="danger" size="sm" disabled>
                        Minh Chứng Không Hợp Lệ
                      </Button>
                    ) : (
                      <Button
                        variant="primary"
                        size="sm"
                        onClick={() => handleOpenModal(registry.id)}
                      >
                        Gửi Minh chứng tham gia
                      </Button>
                    )
                  ) : null}
                </Card.Body>
              </Card>
            </Col>
          );
        })}
      </Row>
      <Modal show={showModal} onHide={handleCloseModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>Minh chứng tham gia</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group controlId="formFile">
            <Form.Label>Chọn ảnh minh chứng:</Form.Label>
            <Form.Control
              type="file"
              accept="image/*"
              onChange={handleFileChange}
            />
          </Form.Group>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Hủy
          </Button>
          <Button
            variant="primary"
            onClick={() => handleSubmitEvidence(selectedRegistry)}
          >
            Gửi minh chứng
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default ActivityRegistries;
