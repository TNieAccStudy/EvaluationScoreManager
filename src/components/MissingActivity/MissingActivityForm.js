// import { useRef } from "react";
// import { Form, Modal, Button } from "react-bootstrap";

// const MissingActivityForm = ({
//   show,
//   onHide,
//   missingActivityData,
//   setMissingActivityData,
//   handleActivityMissing,
// }) => {
//   const proofPicture = useRef();

//   const handleChange = (e) => {
//     const { name, value, type, files } = e.target;
//     if (type === "file") {
//       setMissingActivityData((prev) => ({
//         ...prev,
//         [name]: files[0],
//       }));
//     } else {
//       setMissingActivityData((prev) => ({
//         ...prev,
//         [name]: value,
//       }));
//     }
//   };

//   return (
//     <Modal show={show} onHide={onHide} centered>
//       <Modal.Header closeButton>
//         <Modal.Title>Báo Thiếu Hoạt Động</Modal.Title>
//       </Modal.Header>

//       <Form>
//         <Modal.Body>
//           <Form.Group className="mb-3">
//             <Form.Label>Nội dung</Form.Label>
//             <Form.Control
//               type="text"
//               name="content"
//               value={missingActivityData.content || ""}
//               onChange={handleChange}
//               placeholder="Nhập nội dung báo thiếu"
//               required
//             />
//           </Form.Group>

//           <Form.Group controlId="formFile" className="mb-3">
//             <Form.Label>Ảnh minh chứng</Form.Label>
//             <Form.Control
//               type="file"
//               name="proofPicture"
//               onChange={handleChange}
//               accept="image/*"
//               required
//             />
//           </Form.Group>
//         </Modal.Body>

//         <Modal.Footer>
//           <Button variant="secondary" onClick={onHide}>
//             Hủy
//           </Button>
//           <Button variant="primary" onClick={handleActivityMissing}>
//             Gửi báo thiếu
//           </Button>
//         </Modal.Footer>
//       </Form>
//     </Modal>
//   );
// };

// export default MissingActivityForm;
