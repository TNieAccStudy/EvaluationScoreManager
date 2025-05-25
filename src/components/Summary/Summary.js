import React, { useEffect, useState } from "react";
import { Container, Button, Form, Card, Badge, Modal } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useParams, useSearchParams } from "react-router-dom";

const Summary = () => {
  const [termData, setTermData] = useState([]);
  const [activitiesByTerm, setActivitiesByTerm] = useState({});
  const [showMissingActivity, setShowMissingActivity] = useState(false);
  const [activityAttendanceIds, setActivityAttendanceIds] = useState([]);
  const [activityRegistriesIds, setActivityRegistriesIds] = useState([]);
  const [missingActivity, setMissingActivity] = useState({});
  const [selectedActivityId, setSelectedActivityId] = useState(null);
  const [searchParams] = useSearchParams();
  const { bulletinId } = useParams();
  const semesterId = searchParams.get("semesterId");

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [termsRes, attendanceRes, registryRes] = await Promise.all([
          authApis().get(endpoints["terms"]),
          authApis().get(endpoints["activities-attendances-of-student"]),
          authApis().get(endpoints["activities-registries-of-student"]),
        ]);

        const terms = termsRes.data;
        setTermData(terms);

        const confirmedAttendances = attendanceRes.data
          .filter((item) => item?.censorState === "CONFIRMED")
          .map((item) => item.activityRegistryId.extraActivityId.id);
        setActivityAttendanceIds(confirmedAttendances);

        const registryIds = registryRes.data.map(
          (item) => item.extraActivityId.id
        );
        setActivityRegistriesIds(registryIds);

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
      } catch (err) {
        console.error("Error loading data:", err);
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
        summaryBulletinId: parseInt(bulletinId),
        extraActivityId: selectedActivityId,
      };

      form.append(
        "data",
        new Blob([JSON.stringify(data)], { type: "application/json" })
      );

      const res = await authApis().post(endpoints["missings"], form, {
        headers: { "Content-Type": "multipart/form-data" },
      });

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
      <h4 className="mb-4">Phiếu báo cáo hoạt động ngoại khóa</h4>

      {termData.map((term) => {
        const activities = activitiesByTerm[term.id];
        if (!Array.isArray(activities)) return null;

        return (
          <Container key={term.id} className="mb-5">
            <h5>
              {term.name} (max {term.maxValue} điểm)
            </h5>

            {activities.length === 0 ? (
              <p className="text-muted">Không có hoạt động nào.</p>
            ) : (
              <div className="d-flex flex-wrap gap-3">
                {activities.map((act) => (
                  <Card
                    key={act.id}
                    style={{ minWidth: "280px", flex: "1 0 280px" }}
                  >
                    <Card.Body>
                      <Card.Title>{act.title}</Card.Title>
                      <Card.Text>
                        <strong>Mô tả:</strong> {act.description}
                        <br />
                        <strong>Điểm:</strong> {act.bonusScore}
                        <br />
                        <strong>Trạng thái:</strong> {renderStatusBadge(act.id)}
                      </Card.Text>
                      {activityRegistriesIds.includes(act.id) &&
                        !activityAttendanceIds.includes(act.id) && (
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
                        )}
                    </Card.Body>
                  </Card>
                ))}
              </div>
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
