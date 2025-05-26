import { Badge, Button, Card, Col, Container, Row } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

const Bulletin = () => {
  const [bulletins, setBulletins] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(1);
  const [q] = useSearchParams();
  const nav = useNavigate();
  const loadBulletins = async () => {
    try {
      setLoading(true);
      let url = `${endpoints["bulletins"]}?page=${page}`;
      let kw = q.get("kw");
      if (kw) {
        url = `${url}&kw=${kw}`;
      }
      console.info("Loading bulletins from:", url);
      const res = await authApis().get(url);
      if (res.data.length === 0) setPage(0);
      else {
        if (page === 1) setBulletins(res.data);
        else setBulletins([...bulletins, ...res.data]);
      }
    } catch (err) {
      console.error("Error loading bulletins:", err);
    } finally {
      setLoading(false);
    }
  };

  const handleClick = (event) => {
    if (event.bulletinType === "activity") {
      nav(`/bulletins/${event.id}`);
      return;
    }
    nav(`/bulletins/${event.id}/summary?semesterId=${event.semesterId.id}`);
  };

  const getCardBgColor = (status) => {
    switch (status) {
      case "ACTIVE":
        return "#d4edda";
      case "DEACTIVE":
        return "#e2e3e5";
      case "OPENING":
        return "#d1ecf1";
      case "CLOSE":
        return "#f8d7da";
      default:
        return "#f0f0f0";
    }
  };

  const getTextColor = (status) => {
    switch (status) {
      case "ACTIVE":
        return "#155724";
      case "DEACTIVE":
        return "#383d41";
      case "OPENING":
        return "#0c5460";
      case "CLOSED":
        return "#721c24";
      default:
        return "#212529";
    }
  };

  useEffect(() => {
    if (page > 0) {
      loadBulletins();
    }
  }, [page, q]);

  useEffect(() => {
    setPage(1);
    setBulletins([]);
  }, [q]);

  const loadMore = () => {
    if (!loading && page > 0) setPage(page + 1);
  };

  return (
    <Container className="my-4">
      <h2 className="text-center mb-4 fw-bold">📢 Bản Tin Hoạt Động</h2>
      <Row className="g-4">
        {bulletins
          .filter((event) => event.state !== "CLOSED")
          .map((event, index) => (
            <Col key={index} xs={12} md={6} lg={4}>
              <Card
                className="h-100 shadow-sm border-0"
                onClick={() => handleClick(event)}
                style={{
                  cursor: "pointer",
                  backgroundColor: getCardBgColor(event.state),
                  color: getTextColor(event.state),
                  transition: "transform 0.2s, box-shadow 0.2s",
                }}
                onMouseEnter={(e) => {
                  e.currentTarget.style.transform = "scale(1.03)";
                  e.currentTarget.style.boxShadow =
                    "0 8px 16px rgba(0,0,0,0.25)";
                }}
                onMouseLeave={(e) => {
                  e.currentTarget.style.transform = "scale(1)";
                  e.currentTarget.style.boxShadow = "0 2px 6px rgba(0,0,0,0.1)";
                }}
              >
                <Card.Body>
                  <div className="d-flex justify-content-between align-items-start mb-2">
                    <Badge bg={getStatusVariant(event.state)}>
                      {event.state}
                    </Badge>
                    <small className="text-muted text-end">
                      <div>
                        <i className="bi bi-calendar-event" />{" "}
                        {new Date(event.createdDate).toLocaleDateString()}
                      </div>
                      <div>
                        <i className="bi bi-clock" /> đến{" "}
                        {new Date(event.duration).toLocaleDateString()}
                      </div>
                    </small>
                  </div>

                  <Card.Title className="fw-bold mb-2">
                    {event.title}
                  </Card.Title>

                  <Card.Text
                    className="text-truncate"
                    style={{ maxHeight: "4em", overflow: "hidden" }}
                  >
                    {event.content}
                  </Card.Text>

                  <Card.Text className="text-muted">
                    <i className="bi bi-bookmark-check" /> Học kỳ:{" "}
                    {event.semesterId.name} - {event.semesterId.year}
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          ))}
      </Row>
      {page > 0 && (
        <div className="text-center">
          <Button className="btn btn-primary mt-1 mb-1" onClick={loadMore}>
            Xem thêm...
          </Button>
        </div>
      )}
    </Container>
  );
};

const getStatusVariant = (status) => {
  switch (status) {
    case "ACTIVE":
      return "success";
    case "DEACTIVE":
      return "secondary";
    case "OPENING":
      return "info";
    case "CLOSE":
      return "danger";
    default:
      return "dark";
  }
};

export default Bulletin;
