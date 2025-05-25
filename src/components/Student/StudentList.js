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
  const { activityId } = useParams();
  const [showModal, setShowModal] = useState(false);
  const [selectedRegistry, setSelectedRegistry] = useState(null);
  const [evidenceImage, setEvidenceImage] = useState(null);

  const loadStudentAttendancesIds = useCallback(async () => {
    try {
      setLoading(true);
      const res = await authApis().get(
        endpoints["activities-attendances"](activityId)
      );
      const ids = res.data.map((item) => item.activityRegistryId.studentId.id);
      setStudentAttendancesIds(ids);
    } catch (err) {
      // handle error
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

  const HandleAddListStudentAttendance = () => {
    fileInputRef.current.click();
  };

  const HandleUploadFile = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const csvContent = e.target.result;
        const rows = csvContent.split("\n").filter((row) => row.trim() !== "");
        const data = rows.map((row) => row.split(","));
        const mssvs = data.slice(1).map((row) => row[0]);
        console.log("Danh sách MSSV trong file:", mssvs);
      };
      reader.readAsText(file);
    }
  };
  const handleExportCSV = () => {
    const headers = ["MSSV", "Registry ID", "Ngày tạo", "Tham gia"];
    const rows = studentsRegistries.map((registry) => {
      const student = registry.studentId;
      return [
        student.mssv,
        registry.id,
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

  const handleSubmitEvidence = async (id) => {
    if (!evidenceImage || !selectedRegistry) return;

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
  const handleCloseModal = () => {
    setShowModal(false);
    setEvidenceImage(null);
    setSelectedRegistry(null);
  };

  const handleOpenModal = (registryId) => {
    setSelectedRegistry(registryId);
    setShowModal(true);
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
            <Form.Control
              type="file"
              accept=".csv"
              ref={fileInputRef}
              onChange={HandleUploadFile}
              style={{ display: "none" }}
            />

            <Button
              variant="primary"
              size="sm"
              className="mb-3"
              onClick={HandleAddListStudentAttendance}
            >
              Nạp danh sách điểm danh (.csv)
            </Button>

            <Table striped bordered hover responsive className="text-center">
              <thead className="table-dark">
                <tr>
                  <th>#</th>
                  <th>Avatar</th>
                  <th>MSSV</th>
                  <th>Họ tên</th>
                  <th>Email</th>
                  <th>Thành tích</th>
                  <th>Ngày tạo</th>
                  <th>Trạng thái</th>
                  <th>Thao tác</th>
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
                      <td>{studentInfo.achievement || "Không có"}</td>
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
          variant="success"
          size="sm"
          className="mb-3 ms-2 w-50"
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
