import { useEffect, useRef, useState } from "react";
import { Alert, Button, Form } from "react-bootstrap";
import Apis, { endpoints } from "../configs/Apis";
import MySpinner from "./layouts/MySpinner";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const info = [
    {
      title: "Tên",
      field: "firstName",
      type: "text",
    },
    {
      title: "Họ và tên lót",
      field: "lastName",
      type: "text",
    },
    {
      title: "Email",
      field: "email",
      type: "email",
    },
    {
      title: "Số điện thoại",
      field: "phone",
      type: "tel",
    },
    {
      title: "Tên đăng nhập",
      field: "username",
      type: "text",
    },
    ,
    {
      title: "Mã số sinh viên",
      field: "mssv",
      type: "text",
    },
    {
      title: "Mật khẩu",
      field: "password",
      type: "password",
    },
    {
      title: "Xác nhận mật khẩu",
      field: "confirm",
      type: "password",
    },
  ];
  const [user, setUser] = useState({});
  const avatar = useRef();
  const [msg, setMsg] = useState();
  const [loading, setLoading] = useState(false);
  const [classes, setClasses] = useState([]);
  const [departments, setDepartments] = useState([]);
  const nav = useNavigate();
  const loadDepartment = async () => {
    const res = await Apis.get(endpoints["departments"]);
    console.log(res.data);
    setDepartments(res.data);
  };
  const loadClass = async () => {
    console.log(user.departmentId);
    if (!user.departmentId) return;
    try {
      const res = await Apis.get(
        endpoints["classed-department"](user.departmentId)
      );
      console.log(res.data);
      setClasses(res.data);
    } catch (err) {
      console.error(err);
    }
  };
  useEffect(() => {
    loadDepartment();
  }, []);
  useEffect(() => {
    loadClass();
  }, [user.departmentId]);

  const setState = (value, field) => {
    const newUser = { ...user, [field]: value };
    if (field === "departmentId") {
      newUser["classId"] = "";
    }
    setUser(newUser);
  };

  const register = async (e) => {
    e.preventDefault();
    if (user.password !== user.confirm) {
      setMsg("Mật khẩu KHÔNG khớp");
      return;
    }
    const { departmentId, confirm, classId, ...userData } = user;
    let form = new FormData();
    const data = JSON.stringify({
      ...userData,
      classId: parseInt(classId),
      userType: "student",
      userRole: "ROLE_STUDENT",
    });
    console.log(data);
    const dataBlob = new Blob([data], {
      type: "application/json",
    });
    form.append("data", dataBlob);

    form.append("avatar", avatar.current.files[0]);
    console.log(form.get("data"));
    try {
      setLoading(true);
      await Apis.post(endpoints["register"], form);

      nav("/login");
    } catch (ex) {
      console.error(ex);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <h1 className="text-center text-success mt-1">ĐĂNG KÝ</h1>

      {msg && <Alert variant="danger">{msg}</Alert>}

      <Form onSubmit={register}>
        {info.map((i) => (
          <Form.Control
            value={user[i.field]}
            onChange={(e) => setState(e.target.value, i.field)}
            className="mt-3 mb-1"
            key={i.field}
            type={i.type}
            placeholder={i.title}
            required
          />
        ))}

        <Form.Select
          className="mt-3 mb-1"
          value={user.departmentId || ""}
          onChange={(e) => setState(e.target.value, "departmentId")}
          required
        >
          <option value="">-- Chọn khoa/department --</option>
          {departments.map((d) => (
            <option key={d.id} value={d.id}>
              {d.name}
            </option>
          ))}
        </Form.Select>

        {user.departmentId && (
          <Form.Select
            className="mt-3 mb-1"
            value={user.classId || ""}
            onChange={(e) => setState(e.target.value, "classId")}
            required
          >
            <option value="">-- Chọn lớp --</option>
            {classes.map((c) => (
              <option key={c.id} value={c.id}>
                {c.name}
              </option>
            ))}
          </Form.Select>
        )}
        <Form.Control
          ref={avatar}
          className="mt-3 mb-1"
          type="file"
          placeholder="Ảnh đại diện"
          required
        />

        {loading === true ? (
          <MySpinner />
        ) : (
          <Button type="submit" variant="success" className="mt-3 mb-1">
            Đăng ký
          </Button>
        )}
      </Form>
    </>
  );
};

export default Register;
