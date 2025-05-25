import { useContext, useState } from "react";
import {
  Button,
  Container,
  Dropdown,
  Form,
  Image,
  Nav,
  Navbar,
} from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { MyDispatcherContext, MyUserContext } from "../../configs/MyContexts";
import BulletinForm from "../Bulletin/BulletinForm";
import moment from "moment";
import { authApis, endpoints } from "../../configs/Apis";

const Header = () => {
  const [kw, setKw] = useState("");
  const nav = useNavigate();
  const user = useContext(MyUserContext);
  const dispatch = useContext(MyDispatcherContext);

  const [showBulletinModal, setShowBulletinModal] = useState(false);
  const [bulletinFormData, setBulletinFormData] = useState({});

  const handlePostBulletinSummary = async () => {
    const formattedDate = moment(bulletinFormData.duration).format(
      "YYYY-MM-DD HH:mm:ss"
    );
    const data = {
      ...bulletinFormData,
      semesterId: parseInt(bulletinFormData.semesterId),
      duration: formattedDate,
      bulletinType: "summary",
    };
    try {
      const res = await authApis().post(endpoints["bulletins"], data, {
        headers: { "Content-Type": "application/json" },
      });
      if (res.status === 201) {
        alert("Đăng bản tin thành công");
        setShowBulletinModal(false);
        setBulletinFormData(null);
      } else {
        alert("Đăng bản tin thất bại");
      }
    } catch (err) {
      alert("Post bulletin failed: " + err.message);
    }
  };

  const search = (e) => {
    e.preventDefault();
    nav(`/bulletins?kw=${kw}`);
  };

  return (
    <Navbar expand="lg" bg="light" variant="light" className="shadow-sm py-2">
      <Container>
        <Navbar.Brand as={Link} to="/" className="fw-bold text-primary">
          Manage Training Points
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbar-nav" />
        <Navbar.Collapse id="navbar-nav">
          <Nav className="me-auto align-items-center">
            {/* <Nav.Link as={Link} to="/">
              Trang chủ
            </Nav.Link> */}

            {user === null ? (
              <>
                <Nav.Link as={Link} to="/login" className="text-danger">
                  Đăng nhập
                </Nav.Link>
                <Nav.Link as={Link} to="/register" className="text-success">
                  Đăng ký
                </Nav.Link>
              </>
            ) : (
              <>
                <Dropdown align="end">
                  <Dropdown.Toggle
                    variant="light"
                    className="d-flex align-items-center border-0"
                    style={{ boxShadow: "none" }}
                  >
                    <Image
                      src={user.avatar}
                      alt="Avatar"
                      width={40}
                      height={40}
                      className="rounded-circle me-2"
                      style={{ objectFit: "cover" }}
                    />
                    <span className="fw-semibold text-dark">
                      {user.username}
                    </span>
                  </Dropdown.Toggle>

                  <Dropdown.Menu>
                    {user.userRole !== "ROLE_STUDENTASSISTANT" ? (
                      <>
                        <Dropdown.Item as={Link} to="/message">
                          Chat Student Assistant
                        </Dropdown.Item>
                        <Dropdown.Item as={Link} to="/bulletins">
                          Bản Tin
                        </Dropdown.Item>
                        <Dropdown drop="end">
                          {/* <Dropdown.Toggle as="div" className="dropdown-item">
                            Quản lý hoạt động
                          </Dropdown.Toggle>
                          <Dropdown.Menu> */}
                          <Dropdown.Item as={Link} to="/activities-registries">
                            Hoạt động đã đăng ký
                          </Dropdown.Item>
                          {/* <Dropdown.Item
                              as={Link}
                              to="/activities-attendances"
                            >
                              Đã tham gia
                            </Dropdown.Item> */}
                          {/* </Dropdown.Menu> */}
                        </Dropdown>
                      </>
                    ) : (
                      <>
                        <Dropdown.Item as={Link} to="/chat">
                          List Chat
                        </Dropdown.Item>
                        <Dropdown drop="end">
                          <Dropdown.Toggle as="div" className="dropdown-item">
                            Quản lý hoạt động
                          </Dropdown.Toggle>
                          <Dropdown.Menu>
                            <Dropdown.Item as={Link} to="/activities">
                              Danh sách hoạt động
                            </Dropdown.Item>
                            <Dropdown.Item as={Link} to="/missings-activities">
                              Báo thiếu
                            </Dropdown.Item>
                          </Dropdown.Menu>
                        </Dropdown>
                        <Dropdown.Item as={Link} to="/students">
                          Quản lý sinh viên
                        </Dropdown.Item>
                        <Dropdown.Item
                          as="button"
                          onClick={() => setShowBulletinModal(true)}
                        >
                          Đăng bản tin tổng kết
                        </Dropdown.Item>
                      </>
                    )}

                    <Dropdown.Divider />
                    <Dropdown.Item
                      onClick={() => dispatch({ type: "logout" })}
                      className="text-danger"
                    >
                      Đăng xuất
                    </Dropdown.Item>
                  </Dropdown.Menu>
                </Dropdown>
              </>
            )}
          </Nav>

          <Form className="d-flex" onSubmit={search}>
            <Form.Control
              type="search"
              value={kw}
              onChange={(e) => setKw(e.target.value)}
              placeholder="Tìm kiếm hoạt động..."
              className="me-2"
            />
            <Button type="submit" variant="outline-primary">
              Tìm
            </Button>
          </Form>
        </Navbar.Collapse>
        <BulletinForm
          show={showBulletinModal}
          onHide={() => setShowBulletinModal(false)}
          bulletinData={bulletinFormData}
          setBulletinData={setBulletinFormData}
          onPost={handlePostBulletinSummary}
        />
      </Container>
    </Navbar>
  );
};

export default Header;
