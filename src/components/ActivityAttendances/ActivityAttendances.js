import { useEffect, useState } from "react";
import { authApis, endpoints } from "../../configs/Apis";
import MySpinner from "../layouts/MySpinner";

const ActivityAttendances = () => {
  const [activityAttendances, setActivityAttendances] = useState([]);
  const [loading, setLoading] = useState(true);

  const updateAttendance = async (attendance, newState) => {
    try {
      setLoading(true);
      const updated = { ...attendance, censorState: newState };
      console.log("Updating attendance:", updated);
      const res = await authApis().put(
        endpoints["attendances-detail"](attendance.id),
        updated
      );
      console.log("Updated attendance:", res.data);
      setActivityAttendances((prev) =>
        prev
          .map((a) => (a.id === attendance.id ? res.data : a))
          .filter((a) => a.censorState === "PENDING")
      );
    } catch (err) {
      console.error("Error updating activity attendance:", err);
    } finally {
      setLoading(false);
    }
  };

  const loadActivityAttendances = async () => {
    try {
      setLoading(true);
      const res = await authApis().get(endpoints["attendances"]);
      const pendingAttendances = res.data.filter(
        (a) => a.censorState === "PENDING"
      );
      console.log("Pending attendances:", pendingAttendances);
      setActivityAttendances(pendingAttendances);
    } catch (err) {
      console.error("Error loading activity attendances:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadActivityAttendances();
  }, []);

  return (
    <div className="attendances-activity">
      <h1>Duyệt Attendances</h1>
      {loading ? (
        <MySpinner />
      ) : activityAttendances.length === 0 ? (
        <p>Không có attendance nào cần duyệt.</p>
      ) : (
        activityAttendances.map((attendance) => (
          <div
            key={attendance.id}
            className="attendance-card"
            style={{
              display: "flex",
              border: "1px solid #ccc",
              padding: "1rem",
              marginBottom: "1rem",
              borderRadius: "8px",
              alignItems: "center",
              gap: "1rem",
            }}
          >
            {/* Image on the left */}
            <div>
              <img
                src={attendance.proofPicture}
                alt="proof"
                width="200"
                style={{ borderRadius: "4px", objectFit: "cover" }}
              />
            </div>

            {/* Info on the right */}
            <div style={{ flex: 1 }}>
              <p>
                <strong>ID:</strong> {attendance.id}
              </p>
              <p>
                <strong>State:</strong> {attendance.censorState}
              </p>
              <p>
                <strong>Activity:</strong>{" "}
                {attendance.activityRegistryId.extraActivityId.title}
              </p>
              <p>
                <strong>Student:</strong>{" "}
                {attendance.activityRegistryId.studentId.firstName}{" "}
                {attendance.activityRegistryId.studentId.lastName}
              </p>

              <div style={{ marginTop: "10px", display: "flex", gap: "10px" }}>
                <button
                  onClick={() => updateAttendance(attendance, "CONFIRMED")}
                  style={{
                    backgroundColor: "green",
                    color: "white",
                    padding: "5px 10px",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  Confirm
                </button>

                <button
                  onClick={() => updateAttendance(attendance, "CANCELED")}
                  style={{
                    backgroundColor: "red",
                    color: "white",
                    padding: "5px 10px",
                    border: "none",
                    borderRadius: "4px",
                    cursor: "pointer",
                  }}
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default ActivityAttendances;
