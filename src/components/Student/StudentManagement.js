import React, { useEffect, useState } from "react";
import {
  Table,
  Container,
  Spinner,
  Alert,
  Form,
  Row,
  Col,
} from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";

const StudentManagement = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const [selectedClass, setSelectedClass] = useState("all");
  const [selectedAchievement, setSelectedAchievement] = useState("all");

  const [classOptions, setClassOptions] = useState([]);
  const [achievementOptions, setAchievementOptions] = useState([]);

  const loadStudents = async () => {
    setLoading(true);
    try {
      const response = await authApis().get(endpoints["students"]);
      console.log("Danh sách sinh viên:", response.data);
      setStudents(response.data);

      const uniqueClasses = [
        ...new Set(response.data.map((s) => s.classId?.name).filter(Boolean)),
      ];
      setClassOptions(uniqueClasses);
      const uniqueAchievements = [
        ...new Set(response.data.map((s) => s.achievement)),
      ];
      setAchievementOptions(uniqueAchievements);
    } catch (err) {
      setError("Không thể tải danh sách sinh viên");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadStudents();
  }, []);

  // Hàm lọc sinh viên theo lựa chọn
  const filteredStudents = students.filter((student) => {
    const matchClass =
      selectedClass === "all" || student.classId?.name === selectedClass;
    const matchAchievement =
      selectedAchievement === "all" ||
      student.achievement === selectedAchievement;

    return matchClass && matchAchievement;
  });

  return (
    <Container className="mt-4">
      <h3 className="mb-4 text-center">Danh sách sinh viên</h3>

      {loading && (
        <div className="text-center my-5">
          <Spinner animation="border" variant="primary" />
          <p>Đang tải dữ liệu...</p>
        </div>
      )}

      {error && <Alert variant="danger">{error}</Alert>}

      {!loading && !error && (
        <>
          {/* Select box filter */}
          <Row className="mb-3">
            <Col md={6}>
              <Form.Group controlId="filterClass">
                <Form.Label>Lọc theo lớp</Form.Label>
                <Form.Select
                  value={selectedClass}
                  onChange={(e) => setSelectedClass(e.target.value)}
                >
                  <option value="all">Tất cả</option>
                  {classOptions.map((cls) => (
                    <option key={cls} value={cls}>
                      {cls}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={6}>
              <Form.Group controlId="filterAchievement">
                <Form.Label>Lọc theo thành tích</Form.Label>
                <Form.Select
                  value={selectedAchievement}
                  onChange={(e) => setSelectedAchievement(e.target.value)}
                >
                  <option value="all">Tất cả</option>
                  {achievementOptions.map((ach) => (
                    <option key={ach} value={ach}>
                      {ach}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
          </Row>

          {filteredStudents.length === 0 ? (
            <Alert variant="info">Không có sinh viên phù hợp</Alert>
          ) : (
            <Table striped bordered hover responsive>
              <thead>
                <tr>
                  <th>#</th>
                  <th>MSSV</th>
                  <th>Họ tên</th>
                  <th>Thành tích</th>
                  <th>Lớp</th>
                  <th>Ngày tạo</th>
                </tr>
              </thead>
              <tbody>
                {filteredStudents.map((student, index) => (
                  <tr key={student.id}>
                    <td>{index + 1}</td>
                    <td>{student.mssv}</td>
                    <td>
                      {student.firstName} {student.lastName}
                    </td>
                    <td>{student.achievement}</td>
                    <td>{student.classId?.name}</td>
                    <td>{new Date(student.createdDate).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )}
        </>
      )}
    </Container>
  );
};

export default StudentManagement;
