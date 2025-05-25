import { useEffect, useState } from "react";
import { Form, Button, Modal } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";

const BulletinForm = ({
  show,
  onHide,
  bulletinData,
  setBulletinData,
  onPost,
}) => {
  const [semesters, setSemesters] = useState([]);
  const loadSemesters = async () => {
    try {
      const res = await authApis().get(endpoints["semesters"]);
      setSemesters(res.data);
    } catch (err) {
      console.error("Error loading semesters:", err);
    }
  };
  useEffect(() => {
    loadSemesters();
  }, []);
  const handleChange = (e) => {
    const { name, value } = e.target;
    setBulletinData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  return (
    bulletinData && (
      <Modal show={show} onHide={onHide}>
        <Modal.Header closeButton>
          <Modal.Title>Đăng Bản Tin</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                name="title"
                value={bulletinData.title}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Content</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                name="content"
                value={bulletinData.content}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Duration</Form.Label>
              <Form.Control
                type="datetime-local"
                name="duration"
                value={bulletinData.duration}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Semester</Form.Label>
              <Form.Select
                name="semesterId"
                value={bulletinData.semesterId}
                onChange={handleChange}
                required
              >
                <option value="">-- Select Semester --</option>
                {semesters.map((s) => (
                  <option key={s.id} value={s.id}>
                    {`${s.name} - ${s.year}`}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onHide}>
            Cancel
          </Button>
          <Button variant="primary" onClick={onPost}>
            Post Bulletin
          </Button>
        </Modal.Footer>
      </Modal>
    )
  );
};

export default BulletinForm;
