import React, { useEffect, useState } from "react";
import {
  Container,
  Button,
  Form,
  Card,
  Badge,
  Modal,
  Row,
  Col,
} from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useParams, useSearchParams } from "react-router-dom";
import MySpinner from "../layouts/MySpinner";

const Summary = () => {
  const [termData, setTermData] = useState([]);
  const [activitiesByTerm, setActivitiesByTerm] = useState({});
  const [showMissingActivity, setShowMissingActivity] = useState(false);
  const [activityAttendanceIds, setActivityAttendanceIds] = useState([]);
  const [activityCanceledIds, setActivityCanceledIds] = useState([]);
  const [activityRegistriesIds, setActivityRegistriesIds] = useState([]);
  const [activityMissingsIds, setActivityMissingsIds] = useState([]);
  const [missingActivity, setMissingActivity] = useState({});
  const [selectedActivityId, setSelectedActivityId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [evalScore, setEvalScore] = useState(null);
  const [searchParams] = useSearchParams();
  const { bulletinId } = useParams();
  const semesterId = searchParams.get("semesterId");

  const loadEvalScore = async () => {
    try {
      const res = await authApis().get(endpoints["evalScores-of-student"], {
        params: {
          semester: semesterId,
        },
      });

      setEvalScore(res.data);
    } catch (err) {
      console.error("Error loading evaluation score:", err);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        const [termsRes, attendanceRes, registryRes, missingRes] =
          await Promise.all([
            authApis().get(endpoints["terms"]),
            authApis().get(endpoints["activities-attendances-of-student"]),
            authApis().get(endpoints["activities-registries-of-student"]),
            authApis().get(endpoints["activities-missings-of-student"]),
          ]);

        const terms = termsRes.data;
        setTermData(terms);

        const confirmedAttendances = attendanceRes.data
          .filter((item) => item?.censorState === "CONFIRMED")
          .map((item) => item.activityRegistryId.extraActivityId.id);
        setActivityAttendanceIds(confirmedAttendances);

        const canceledAttendances = attendanceRes.data
          .filter((item) => item?.censorState === "CANCELED")
          .map((item) => item.activityRegistryId.extraActivityId.id);
        setActivityCanceledIds(canceledAttendances);

        const registryIds = registryRes.data.map(
          (item) => item.extraActivityId.id
        );
        setActivityRegistriesIds(registryIds);

        const missingIds = missingRes.data.map(
          (item) => item.extraActivityId.id
        );
        console.log("Missing activity IDs:", missingIds);
        setActivityMissingsIds(missingIds);

        if (semesterId) {
          const allActivities = {};
          for (const term of terms) {
            try {
              const actRes = await authApis().get(endpoints["activities"], {
                params: {
                  term: term.id,
                  semester: semesterId,
                },
              });
              allActivities[term.id] = actRes.data;
            } catch (err) {
              console.error(
                `Error loading activities for term ${term.id}:`,
                err
              );
            }
          }
          setActivitiesByTerm(allActivities);
        }

        await loadEvalScore();
      } catch (err) {
        console.error("Error loading data:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [semesterId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMissingActivity((prev) => ({ ...prev, [name]: value }));
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setMissingActivity((prev) => ({ ...prev, proofPicture: file }));
  };

  const handleCloseModal = () => {
    setShowMissingActivity(false);
    setMissingActivity({});
    setSelectedActivityId(null);
  };

  const handleActivityMissing = async () => {
    const { content, proofPicture } = missingActivity;
    if (!content || !proofPicture || !selectedActivityId) return;

    try {
      const form = new FormData();
      form.append("proofPicture", proofPicture);

      const data = {
        proofContent: content,
        summaryBulletinId: {
          bulletinType: "summary",
          id: parseInt(bulletinId),
        },
        extraActivityId: selectedActivityId,
      };

      form.append(
        "data",
        new Blob([JSON.stringify(data)], { type: "application/json" })
      );

      const res = await authApis().post(endpoints["missings"], form, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      setActivityMissingsIds((prev) => [...prev, selectedActivityId]);
      console.log("Missing activity submitted:", res.data);
      handleCloseModal();
    } catch (err) {
      console.error("Error sending missing activity:", err);
    }
  };

  const calculateTotalScore = (activities) =>
    activities
      .filter((act) => activityAttendanceIds.includes(act.id))
      .reduce((sum, act) => sum + act.bonusScore, 0);

  const renderStatusBadge = (activityId) => {
    if (activityRegistriesIds.includes(activityId)) {
      if (activityAttendanceIds.includes(activityId)) {
        return <Badge bg="success">Đã tham gia</Badge>;
      } else {
        return <Badge bg="warning">Chưa tham gia</Badge>;
      }
    }
    return <Badge bg="secondary">Chưa đăng ký</Badge>;
  };

  return (
    <Container className="mt-4">
      <h4 className="mb-4 text-center">Phiếu báo cáo hoạt động ngoại khóa</h4>

      {loading && <MySpinner />}

      {!loading && evalScore && (
        <>
          <div className="text-center fw-bold text-info mb-4">
            Điểm rèn luyện của bạn là: {evalScore.totalScore} điểm
          </div>
          <div className="text-center fw-bold text-info mb-4">
            Thành Tích: {evalScore.achievement}
          </div>
        </>
      )}

      {termData.map((term) => {
        const activities = activitiesByTerm[term.id];
        if (!Array.isArray(activities)) return null;

        return (
          <Container key={term.id} className="mb-5">
            <h5 className="mb-3 border-bottom pb-2">
              {term.name} (Tối đa {term.maxValue} điểm)
            </h5>

            {activities.length === 0 ? (
              <p className="text-muted">Không có hoạt động nào.</p>
            ) : (
              <Row className="g-4">
                {activities.map((act) => (
                  <Col key={act.id} md={6} lg={4}>
                    <Card className="shadow-sm h-100">
                      <Card.Body>
                        <Card.Title>{act.title}</Card.Title>
                        <Card.Text>
                          <strong>Mô tả:</strong> {act.description}
                          <br />
                          <strong>Điểm:</strong> {act.bonusScore}
                          <br />
                          <strong>Trạng thái:</strong>{" "}
                          {renderStatusBadge(act.id)}
                        </Card.Text>
                        {activityRegistriesIds.includes(act.id) &&
                          !activityAttendanceIds.includes(act.id) &&
                          (activityCanceledIds.includes(act.id) ? (
                            <span className="text-danger small">
                              Minh chứng không hợp lệ
                            </span>
                          ) : activityMissingsIds.includes(act.id) ? (
                            <span className="text-muted small">
                              Đã gửi yêu cầu báo thiếu
                            </span>
                          ) : (
                            <Button
                              variant="outline-danger"
                              size="sm"
                              onClick={() => {
                                setSelectedActivityId(act.id);
                                setShowMissingActivity(true);
                              }}
                            >
                              Báo thiếu
                            </Button>
                          ))}
                      </Card.Body>
                    </Card>
                  </Col>
                ))}
              </Row>
            )}
            <div className="fw-bold mt-3">
              Tổng điểm đạt được: {calculateTotalScore(activities)} /{" "}
              {term.maxValue} điểm
            </div>
          </Container>
        );
      })}

      <Modal show={showMissingActivity} onHide={handleCloseModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>Minh chứng tham gia</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form.Group className="mb-3">
            <Form.Label>Nội dung</Form.Label>
            <Form.Control
              type="text"
              name="content"
              value={missingActivity.content || ""}
              onChange={handleChange}
              placeholder="Nhập nội dung báo thiếu"
              required
            />
          </Form.Group>
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
          <Button variant="primary" onClick={handleActivityMissing}>
            Gửi minh chứng
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default Summary;
