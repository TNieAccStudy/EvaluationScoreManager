import { useEffect, useState } from "react";
import Apis, { endpoints } from "../../configs/Apis";

const ActivityAttendances = () => {
  const [activityAttendances, setActivityAttendances] = useState([]);
  const [loading, setLoading] = useState(true);
  const loadActivityAttendances = async () => {
    const res = await Apis.get(endpoints["activities-attendances-of-student"]);
    setActivityAttendances(res.data);
  };
  useEffect(() => {
    loadActivityAttendances();
  }, []);

  return (
    <div className="attendances-activity">
      <h1>Attendances Activity</h1>
    </div>
  );
};
export default ActivityAttendances;
