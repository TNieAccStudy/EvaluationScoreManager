import { useEffect, useState } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import { Card, Form, Spinner } from "react-bootstrap";

const MissingActivity = () => {
  const [missingActivities, setMissingActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [departments, setDepartments] = useState("");
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const loadDepartments = async () => {
    try {
      const res = await authApis().get(endpoints["departments"]);
      setDepartments(res.data);
    } catch (err) {
      console.error("Error loading departments:", err);
    }
  };
  const handleConfirm = async (activityId) => {
    try {
      setLoading(true);
      await authApis().post(endpoints["confirm-missing"](activityId));
      // Reload missing activities after confirmation
      loadMissingActivities();
    } catch (err) {
      console.error("Error confirming missing activity:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (activityId) => {
    try {
      setLoading(true);
      await authApis().post(endpoints["cancel-missing"](activityId));
      // Reload missing activities after cancellation
      loadMissingActivities();
    } catch (err) {
      console.error("Error cancelling missing activity:", err);
    } finally {
      setLoading(false);
    }
  };

  const loadMissingActivities = async () => {
    try {
      const res = await authApis().get(endpoints["missings"]);
      setMissingActivities(res.data);
    } catch (err) {
      console.error("Error loading missing activities:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDepartments();
    loadMissingActivities();
  }, []);

  return (
    <div className="container mt-4">
      <h2 className="mb-4">Danh sách Báo Thiếu Hoạt Động</h2>

      <Form.Group className="mb-4">
        <Form.Label htmlFor="filter">Lọc theo Khoa</Form.Label>
        <Form.Select
          value={selectedDepartment}
          onChange={(e) => setSelectedDepartment(e.target.value)}
          id="filter"
          className="mb-3"
        >
          <option value="">Tất cả</option>
          {departments &&
            departments.map((department) => (
              <option key={department.id} value={department.id}>
                {department.name}
              </option>
            ))}
        </Form.Select>
      </Form.Group>

      {loading ? (
        <div className="text-center">
          <Spinner animation="border" variant="primary" />
        </div>
      ) : missingActivities.length === 0 ? (
        <p>Không có báo thiếu nào.</p>
      ) : (
        missingActivities.map((item) => (
          <Card className="mb-3 shadow-sm" key={item.id}>
            <Card.Body>
              <Card.Title>Hoạt động: {item.extraActivityId?.name}</Card.Title>
              <Card.Subtitle className="mb-2 text-muted">
                Trạng thái: <strong>{item.executedStatus}</strong>
              </Card.Subtitle>
              <Card.Text>
                <strong>Nội dung:</strong> {item.proofContent}
              </Card.Text>
              {item.proofPicture && (
                <img
                  src={item.proofPicture}
                  alt="Ảnh minh chứng"
                  style={{
                    maxWidth: "100%",
                    height: "auto",
                    marginTop: "10px",
                  }}
                />
              )}
              <hr />
              <p>
                <strong>Sinh viên:</strong> {item.studentId?.username}
              </p>

              {/* ✅ Thêm 2 nút Confirm & Cancel */}
              <div className="d-flex gap-2 mt-3">
                <button
                  className="btn btn-success"
                  onClick={() => handleConfirm(item.id)}
                >
                  ✅ Xác nhận
                </button>
                <button
                  className="btn btn-danger"
                  onClick={() => handleCancel(item.id)}
                >
                  ❌ Hủy bỏ
                </button>
              </div>
            </Card.Body>
          </Card>
        ))
      )}
    </div>
  );
};

export default MissingActivity;
