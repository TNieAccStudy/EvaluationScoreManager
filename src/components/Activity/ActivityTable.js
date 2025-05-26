import React, { useEffect, useState } from "react";
import { Table, Button, OverlayTrigger, Tooltip, Badge } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";

const ActivityTable = ({
  activities,
  onEdit,
  onDelete,
  onViewStudents,
  onShowBulletin,
}) => {
  const [extraActivityIds, setExtraActivityIds] = useState([]);

  const loadBulletins = async () => {
    try {
      let allBulletins = [];
      let currentPage = 1;
      let hasMore = true;

      while (hasMore) {
        const res = await authApis().get(endpoints["bulletins"], {
          params: { page: currentPage },
        });

        allBulletins = [...allBulletins, ...res.data];
        hasMore = res.data.length !== 0;
        currentPage += 1;
      }

      const extraIds = allBulletins
        .filter((item) => item.extraActivity !== null)
        .map((item) => item.extraActivity.id);

      setExtraActivityIds(extraIds);
    } catch (err) {
      console.error("Error loading bulletins:", err);
    }
  };

  useEffect(() => {
    loadBulletins();
  }, []);

  const renderTooltip = (text) => (
    <Tooltip id={`tooltip-${text}`}>{text}</Tooltip>
  );

  return (
    <div className="table-responsive shadow-sm p-3 rounded bg-white">
      <h4 className="mb-4 text-center text-primary fw-bold">Activity List</h4>
      <Table bordered hover className="align-middle text-center">
        <thead className="table-primary">
          <tr>
            <th>Title</th>
            <th>Bonus Score</th>
            <th>Semester</th>
            <th>Term</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {activities.length === 0 ? (
            <tr>
              <td colSpan="5" className="text-center text-muted py-4">
                No activities found.
              </td>
            </tr>
          ) : (
            activities.map((item) => (
              <tr key={item.id}>
                <td className="fw-semibold">{item.title}</td>
                <td>
                  <Badge bg="info">{item.bonusScore}</Badge>
                </td>
                <td>
                  <Badge bg="secondary">
                    {item.semesterId?.name} {item.semesterId?.year}
                  </Badge>
                </td>
                <td>
                  <Badge bg="light" text="dark">
                    {item.termId?.name}
                  </Badge>
                </td>
                <td>
                  <div className="d-flex gap-2 justify-content-center flex-wrap">
                    <OverlayTrigger
                      placement="top"
                      overlay={renderTooltip("Edit activity")}
                    >
                      <Button
                        size="sm"
                        variant="warning"
                        onClick={() => onEdit(item)}
                      >
                        ✏️
                      </Button>
                    </OverlayTrigger>

                    <OverlayTrigger
                      placement="top"
                      overlay={renderTooltip("Delete activity")}
                    >
                      <Button
                        size="sm"
                        variant="danger"
                        onClick={() => onDelete(item.id)}
                      >
                        🗑️
                      </Button>
                    </OverlayTrigger>

                    {!extraActivityIds.includes(item.id) ? (
                      <OverlayTrigger
                        placement="top"
                        overlay={renderTooltip("Post Bulletin")}
                      >
                        <Button
                          size="sm"
                          variant="primary"
                          onClick={() => {
                            onShowBulletin(item.id);
                            setExtraActivityIds((prev) => [...prev, item.id]);
                          }}
                        >
                          📰 Đăng Bản Tin
                        </Button>
                      </OverlayTrigger>
                    ) : (
                      <OverlayTrigger
                        placement="top"
                        overlay={renderTooltip("View Students")}
                      >
                        <Button
                          size="sm"
                          variant="success"
                          onClick={() => onViewStudents(item.id)}
                        >
                          👥 Xem Sinh Viên
                        </Button>
                      </OverlayTrigger>
                    )}
                  </div>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </Table>
    </div>
  );
};

export default ActivityTable;
