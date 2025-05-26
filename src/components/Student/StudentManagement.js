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
  const [semesters, setSemesters] = useState([]);
  const [selectedSemester, setSelectedSemester] = useState(1);

  const loadEvalScore = async (studentId) => {
    try {
      const res = await authApis().get(
        endpoints["evalScores-of-student-detail"](studentId),
        {
          params: { semester: selectedSemester },
        }
      );
      return res.data;
    } catch (err) {
      console.error("Error loading evaluation score:", err);
      return null;
    }
  };

  const loadSemesters = async () => {
    try {
      setLoading(true);
      const res = await authApis().get(endpoints["semesters"]);
      setSemesters(res.data);
    } catch (err) {
      console.error("Error loading semesters:", err);
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const loadStudents = async () => {
    setLoading(true);
    try {
      const response = await authApis().get(endpoints["students"]);

      const mergedStudents = await Promise.all(
        response.data.map(async (student) => {
          const resScore = await loadEvalScore(student.username);
          return {
            ...student,
            evalScore: resScore,
          };
        })
      );

      setStudents(mergedStudents);

      const uniqueClasses = [
        ...new Set(response.data.map((s) => s.classId?.name)),
      ];
      setClassOptions(uniqueClasses);
      const achievements = [
        ...new Set(
          mergedStudents
            .map((s) => s.evalScore?.achievement)
            .filter((ach) => ach !== null && ach !== undefined)
        ),
      ];
      setAchievementOptions(achievements);
    } catch (err) {
      setError("Không thể tải danh sách sinh viên");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSemesters();
  }, []);

  useEffect(() => {
    loadStudents();
  }, [selectedSemester]);

  const filteredStudents = students.filter((student) => {
    const matchClass =
      selectedClass === "all" || student.classId?.name === selectedClass;
    const matchAchievement =
      selectedAchievement === "all" ||
      student.evalScore?.achievement === selectedAchievement;

    return matchClass && matchAchievement;
  });

  const exportToCSV = () => {
    if (filteredStudents.length === 0) return;

    const headers = [
      "STT",
      "MSSV",
      "Họ tên",
      "Lớp",
      "Điểm đánh giá",
      "Thành tích (ĐG)",
      "Ngày tạo",
    ];

    const rows = filteredStudents.map((student, index) => [
      index + 1,
      student.mssv,
      `${student.firstName} ${student.lastName}`,
      student.classId?.name || "",
      student.evalScore?.totalScore || "",
      student.evalScore?.achievement || "",
      new Date(student.createdDate).toLocaleString("vi-VN"),
    ]);

    const csvContent =
      "\uFEFF" + // Thêm BOM để Excel đọc đúng tiếng Việt
      [headers, ...rows]
        .map((row) =>
          row.map((item) => `"${String(item).replace(/"/g, '""')}"`).join(",")
        )
        .join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.setAttribute("href", url);
    link.setAttribute("download", "report_danh_sach_sinh_vien.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <Container className="mt-4">
      <h3 className="mb-4 text-center">Danh sách sinh viên</h3>

      <Row className="mb-3">
        <Col className="text-end">
          <button className="btn btn-success" onClick={exportToCSV}>
            Export to CSV
          </button>
        </Col>
      </Row>

      {loading && (
        <div className="text-center my-5">
          <Spinner animation="border" variant="primary" />
          <p>Đang tải dữ liệu...</p>
        </div>
      )}

      {error && <Alert variant="danger">{error}</Alert>}

      {!loading && !error && (
        <>
          <Row className="mb-3">
            <Col md={4}>
              <Form.Group controlId="filterSemester">
                <Form.Label>Lọc theo học kỳ</Form.Label>
                <Form.Select
                  value={selectedSemester}
                  onChange={(e) => setSelectedSemester(e.target.value)}
                >
                  {semesters.map((sem) => (
                    <option key={sem.id} value={sem.id}>
                      {sem.name}
                    </option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col md={4}>
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
            <Col md={4}>
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
                  <th>Lớp</th>
                  <th>Điểm đánh giá</th>
                  <th>Thành tích (ĐG)</th>
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
                    <td>{student.classId?.name}</td>
                    <td>{student.evalScore?.totalScore}</td>
                    <td>{student.evalScore?.achievement}</td>
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
