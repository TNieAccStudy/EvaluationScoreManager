import React from "react";
import { Modal, Form, Button } from "react-bootstrap";

const ActivityForm = ({
  show,
  onHide,
  semesters,
  terms,
  formData,
  setActivityFormData,
  onSave,
}) => {
  const isEditing = Boolean(formData?.id);
  const handleChange = (e) => {
    const { name, value } = e.target;
    const intFields = ["semesterId", "termId", "bonusScore"];
    setActivityFormData((prev) => ({
      ...prev,
      [name]: intFields.includes(name) ? parseInt(value) : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData, isEditing);
  };

  return (
    <Modal show={show} onHide={onHide} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>{isEditing ? "Edit" : "Add"} Activity</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Title</Form.Label>
            <Form.Control
              type="text"
              name="title"
              value={formData.title || ""}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Description</Form.Label>
            <Form.Control
              as="textarea"
              name="description"
              value={formData.description || ""}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Bonus Score</Form.Label>
            <Form.Control
              type="number"
              name="bonusScore"
              value={formData.bonusScore}
              onChange={handleChange}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Semester</Form.Label>
            <Form.Select
              name="semesterId"
              value={formData.semesterId || ""}
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

          <Form.Group className="mb-3">
            <Form.Label>Term</Form.Label>
            <Form.Select
              name="termId"
              value={formData.termId || ""}
              onChange={handleChange}
              required
            >
              <option value="">-- Select Term --</option>
              {terms.map((t) => (
                <option key={t.id} value={t.id}>
                  {t.name}
                </option>
              ))}
            </Form.Select>
          </Form.Group>

          <Button type="submit" variant="success">
            {isEditing ? "Update" : "Create"}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ActivityForm;
