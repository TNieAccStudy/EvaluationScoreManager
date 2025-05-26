import React, { useCallback, useEffect, useRef, useState } from "react";
import {
  Table,
  Container,
  Spinner,
  Alert,
  Button,
  Form,
  Image,
  Card,
  Modal,
} from "react-bootstrap";
import { useParams } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";

const StudentList = () => {
  const [studentsRegistries, setStudentsRegistries] = useState([]);
  const [studentAttendancesIds, setStudentAttendancesIds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const fileInputRef = useRef(null);
  const imageInputRef = useRef(null);
  const { activityId } = useParams();
  const [showModal, setShowModal] = useState(false);
  const [selectedRegistry, setSelectedRegistry] = useState(null);
  const [evidenceImage, setEvidenceImage] = useState(null);

  const [csvData, setCsvData] = useState(null);
  const [imageFile, setImageFile] = useState(null);

  const handleSubmitEvidence = async (id) => {
    if (!evidenceImage) return;

    const form = new FormData();
    form.append("proofPicture", evidenceImage);

    const data = JSON.stringify({
      censorState: "CONFIRMED",
      activityRegistryId: id,
    });

    const dataBlob = new Blob([data], { type: "application/json" });
    form.append("data", dataBlob);
    try {
      const res = await authApis().post(endpoints["attendances"], form);
      console.log("Response from server:", res.data);
      await loadStudentAttendancesIds();
      handleCloseModal();
    } catch (err) {
      alert("Lỗi khi gửi minh chứng: " + err.message);
    }
  };
  const handleFileChange = (e) => {
    setEvidenceImage(e.target.files[0]);
  };
  const handleOpenModal = (registryId) => {
    setSelectedRegistry(registryId);
    setShowModal(true);
  };
  const handleCloseModal = () => {
    setShowModal(false);
    setEvidenceImage(null);
    setSelectedRegistry(null);
  };
  const loadStudentAttendancesIds = useCallback(async () => {
    try {
      setLoading(true);
      const res = await authApis().get(
        endpoints["activities-attendances"](activityId)
      );
      const ids = res.data.map((item) => item.activityRegistryId.studentId.id);
      setStudentAttendancesIds(ids);
    } catch {
    } finally {
      setLoading(false);
    }
  }, [activityId]);

  const loadStudentsRegistries = useCallback(async () => {
    try {
      setLoading(true);
      const res = await authApis().get(
        endpoints["activities-registries"](activityId)
      );
      setStudentsRegistries(res.data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [activityId]);

  const handleUploadCSVClick = () => fileInputRef.current.click();
  const handleUploadImageClick = () => imageInputRef.current.click();

  const handleCSVChange = (event) => {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (e) => {
      const rows = e.target.result
        .split("\n")
        .filter((row) => row.trim() !== "")
        .map((row) => row.replace("\r", "").split(","));

      const attendedStudents = rows
        .slice(1)
        .filter((row) => row[3]?.trim() === "TRUE")
        .map((row) => row[0]?.trim());

      console.log("Danh sách sinh viên:", attendedStudents);
      setCsvData(attendedStudents);
    };
    reader.readAsText(file);
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImageFile(file);
  };

  const handleSubmitAttendanceList = async () => {
    try {
      setLoading(true);
      if (!csvData || !imageFile) {
        alert("Vui lòng nạp cả danh sách sinh viên và ảnh minh chứng.");
        return;
      }

      const form = new FormData();
      const data = JSON.stringify({
        attendedStudents: csvData,
        extraActivityId: parseInt(activityId),
      });
      console.log("Dữ liệu điểm danh:", data);
      const jsonBlob = new Blob([data], { type: "application/json" });

      form.append("data", jsonBlob);
      form.append("proofPictureGeneral", imageFile);
      console.log("Dữ liệu gửi:", form.get("data"));
      console.log("Ảnh gửi:", imageFile.name);

      try {
        const res = await authApis().post(
          endpoints["assistants-upload-csv"],
          form
        );
        console.log("Kết quả gửi:", res.data);
        alert("Gửi danh sách điểm danh thành công!");
        await loadStudentAttendancesIds();
        setCsvData(null);
        setImageFile(null);
      } catch (err) {
        alert("Lỗi khi gửi: " + err.message);
      }
    } catch (error) {
      console.error("Lỗi khi gửi danh sách điểm danh:", error);
      alert("Đã xảy ra lỗi khi gửi danh sách điểm danh. Vui lòng thử lại sau.");
    } finally {
      setLoading(false);
    }
  };
  const handleExportCSV = () => {
    const headers = ["MSSV", "Activity ID", "Ngày tạo", "Tham gia"];
    const rows = studentsRegistries.map((registry) => {
      const student = registry.studentId;
      const extraActivity = registry.extraActivityId;
      return [
        student.mssv,
        extraActivity.id,
        new Date(registry.createdDate).toLocaleDateString(),
        false,
      ];
    });

    const csvContent =
      "\uFEFF" + [headers, ...rows].map((e) => e.join(",")).join("\n");

    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.setAttribute("download", "danh_sach_sinh_vien.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  useEffect(() => {
    loadStudentAttendancesIds();
    loadStudentsRegistries();
  }, [loadStudentAttendancesIds, loadStudentsRegistries]);

  return (
    <Container className="my-4">
      <Card className="p-4 shadow-sm">
        <h3 className="text-center mb-4 text-primary">
          Danh sách sinh viên đăng ký hoạt động
        </h3>

        {loading && (
          <div className="text-center my-5">
            <Spinner animation="border" variant="primary" />
            <p className="mt-3">Đang tải dữ liệu...</p>
          </div>
        )}

        {error && <Alert variant="danger">{error}</Alert>}

        {!loading && !error && (
          <>
            {/* Hidden inputs */}
            <Form.Control
              type="file"
              accept=".csv"
              ref={fileInputRef}
              onChange={handleCSVChange}
              style={{ display: "none" }}
            />
            <Form.Control
              type="file"
              accept="image/*"
              ref={imageInputRef}
              onChange={handleImageChange}
              style={{ display: "none" }}
            />

            {/* Buttons */}
            <div className="d-flex gap-2 mb-3">
              <Button
                variant="primary"
                size="sm"
                onClick={handleUploadCSVClick}
              >
                Nạp danh sách điểm danh (.csv)
              </Button>
              <Button variant="info" size="sm" onClick={handleUploadImageClick}>
                Nạp ảnh minh chứng chung
              </Button>
              <Button
                variant="success"
                size="sm"
                onClick={handleSubmitAttendanceList}
              >
                Gửi minh chứng + danh sách
              </Button>
            </div>

            <Table striped bordered hover responsive className="text-center">
              <thead className="table-dark">
                <tr>
                  <th>#</th>
                  <th>Avatar</th>
                  <th>MSSV</th>
                  <th>Họ tên</th>
                  <th>Email</th>
                  <th>Ngày tạo</th>
                  <th>Trạng thái</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {studentsRegistries.map((registry, index) => {
                  const studentInfo = registry.studentId;
                  const isAlreadyAttended = studentAttendancesIds.includes(
                    studentInfo.id
                  );

                  return (
                    <tr key={`${registry.id}-${index}`}>
                      <td>{index + 1}</td>
                      <td>
                        <Image
                          src={studentInfo.avatar}
                          roundedCircle
                          width={40}
                          height={40}
                          alt="avatar"
                        />
                      </td>
                      <td>{studentInfo.mssv}</td>
                      <td>{`${studentInfo.firstName} ${studentInfo.lastName}`}</td>
                      <td>{studentInfo.username}</td>
                      <td>
                        {new Date(registry.createdDate).toLocaleDateString()}
                      </td>
                      <td>
                        {isAlreadyAttended ? (
                          <span className="text-success fw-bold">
                            Đã Tham Gia
                          </span>
                        ) : (
                          <span className="text-danger fw-bold">Vắng</span>
                        )}
                      </td>
                      <td>
                        {!isAlreadyAttended && (
                          <Button
                            variant="success"
                            size="sm"
                            onClick={() => handleOpenModal(registry.id)}
                          >
                            Xác nhận
                          </Button>
                        )}
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </Table>
          </>
        )}

        <Button
          variant="secondary"
          size="sm"
          className="mb-3 w-50"
          onClick={handleExportCSV}
        >
          Xuất danh sách (.csv)
        </Button>
      </Card>
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

export default StudentList;
