import React, { useEffect } from "react";
import { Table, Button } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";

const ActivityTable = ({
  activities,
  onEdit,
  onDelete,
  onViewStudents,
  onShowBulletin,
}) => {
  const [extraActivityIds, setExtraActivityIds] = React.useState([]);
  const loadBulletins = async () => {
    try {
      const res = await authApis().get(endpoints["bulletins"]);
      const extraIds = res.data
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
  return (
    <Table bordered hover responsive>
      <thead>
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
            <td colSpan="6" className="text-center">
              No activities found.
            </td>
          </tr>
        ) : (
          activities.map((item) => (
            <tr key={item.id}>
              <td>{item.title}</td>
              <td>{item.bonusScore}</td>
              <td>
                {item.semesterId?.name} {item.semesterId?.year}
              </td>
              <td>{item.termId?.name}</td>
              <td>
                <Button
                  size="sm"
                  variant="warning"
                  onClick={() => onEdit(item)}
                >
                  Edit
                </Button>{" "}
                <Button
                  size="sm"
                  variant="danger"
                  onClick={() => onDelete(item.id)}
                >
                  Delete
                </Button>{" "}
                <Button
                  size="sm"
                  variant="success"
                  onClick={() => onViewStudents(item.id)}
                >
                  List Student
                </Button>{" "}
                {!extraActivityIds.includes(item.id) && (
                  <Button
                    size="sm"
                    variant="primary"
                    onClick={() => onShowBulletin(item.id)}
                  >
                    Đăng Bản Tin
                  </Button>
                )}
              </td>
            </tr>
          ))
        )}
      </tbody>
    </Table>
  );
};

export default ActivityTable;
