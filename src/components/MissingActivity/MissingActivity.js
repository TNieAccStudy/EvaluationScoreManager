import { useEffect, useState } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import { Card, Form, Spinner } from "react-bootstrap";

const MissingActivity = () => {
  const [missingActivities, setMissingActivities] = useState([]);
  const [loading, setLoading] = useState(true);
  const [departmentsOptions, setDeparmentOptions] = useState([]);
  const [selectedDepartment, setSelectedDepartment] = useState("");
  const handleUpdateState = async (missing, newState) => {
    try {
      setLoading(true);
      console.log("Updating missing activity:", newState);
      await authApis().patch(endpoints["missing-response"](missing.id), {
        executeStatus: newState,
      });
      loadMissingActivities();
    } catch (err) {
      console.error("Error confirming missing activity:", err);
    } finally {
      setLoading(false);
    }
  };

  const loadMissingActivities = async () => {
    try {
      const res = await authApis().get(endpoints["missings"]);
      const uniqueDeparments = [
        ...new Set(
          res.data.map(
            (missing) => missing?.studentId?.classId?.departmentId.name
          )
        ),
      ];
      console.log("Unique departments:", uniqueDeparments);
      setDeparmentOptions(uniqueDeparments);
      setMissingActivities(
        res.data.filter((missing) => missing.executedStatus === "PENDING")
      );
    } catch (err) {
      console.error("Error loading missing activities:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
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
          {departmentsOptions &&
            departmentsOptions.map((department) => (
              <option key={department} value={department}>
                {department}
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
              <Card.Title>Hoạt động: {item.extraActivityId?.title}</Card.Title>
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
                    maxWidth: "100px",
                    height: "100px",
                    marginTop: "10px",
                  }}
                />
              )}
              <hr />
              <p>
                <strong>Sinh viên:</strong> {item.studentId?.username}
              </p>
              <p>
                <strong>MSSV:</strong> {item.studentId?.mssv}
              </p>
              <p>
                <strong>Lớp:</strong> {item.studentId?.classId.name}
              </p>

              <div className="d-flex gap-2 mt-3">
                <button
                  className="btn btn-success"
                  onClick={() => handleUpdateState(item, "CONFIRMED")}
                >
                  ✅ Xác nhận
                </button>
                <button
                  className="btn btn-danger"
                  onClick={() => handleUpdateState(item, "CANCELED")}
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
