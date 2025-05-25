import React, { useContext, useEffect, useState } from "react";
import { Container, Row, Col, Button } from "react-bootstrap";
import { MyUserContext } from "../../configs/MyContexts";
import ActivityForm from "./ActivityForm";
import ActivityTable from "./ActivityTable";
import BulletinForm from "../Bulletin/BulletinForm";
import moment from "moment";
import { useNavigate } from "react-router-dom";
import { authApis, endpoints } from "../../configs/Apis";

const ActivityManagement = () => {
  const user = useContext(MyUserContext);
  const [activities, setActivities] = useState([]);
  const [semesters, setSemesters] = useState([]);
  const [terms, setTerms] = useState([]);

  const [showActivityModal, setShowActivityModal] = useState(false);
  const [showBulletinModal, setShowBulletinModal] = useState(false);
  const nav = useNavigate();
  const [activityFormData, setActivityFormData] = useState({
    title: "",
    description: "",
    bonusScore: 0,
    semesterId: "",
    termId: "",
    studentAssistantId: null,
  });
  const [bulletinFormData, setBulletinFormData] = useState({
    title: "",
    content: "",
    duration: "",
    semesterId: "",
    extraActivityId: null,
  });

  const loadData = async () => {
    try {
      const [termRes, semesterRes, activityRes] = await Promise.all([
        authApis().get(endpoints["terms"]),
        authApis().get(endpoints["semesters"]),
        authApis().get(endpoints["activities"]),
      ]);
      setTerms(termRes.data);
      setSemesters(semesterRes.data);
      setActivities(activityRes.data);
    } catch (err) {
      console.error("Failed to load data:", err);
    }
  };
  useEffect(() => {
    loadData();
  }, []);

  const handleAddActivity = () => {
    setActivityFormData({
      title: "",
      description: "",
      bonusScore: 0,
      semesterId: "",
      termId: "",
      studentAssistantId: user
        ? { id: user.id, userType: "studentAssistant" }
        : null,
    });
    setShowActivityModal(true);
  };

  const handleEditActivity = (activity) => {
    setActivityFormData({
      ...activity,
      semesterId: parseInt(activity.semesterId),
      termId: parseInt(activity.termId),
    });
    setShowActivityModal(true);
  };

  const handleSaveActivity = async (formData, isEditing) => {
    try {
      if (isEditing) {
        const res = await authApis().put(
          endpoints["activity-detail"](formData.id),
          formData
        );
        setActivities((prev) =>
          prev.map((a) => (a.id === formData.id ? res.data : a))
        );
      } else {
        const res = await authApis().post(endpoints["activities"], formData, {
          headers: { "Content-Type": "application/json" },
        });
        setActivities((prev) => [...prev, res.data]);
      }
      setShowActivityModal(false);
    } catch (err) {
      alert("Save failed: " + err.message);
    }
  };

  const handleDeleteActivity = async (id) => {
    if (window.confirm("Are you sure you want to delete this activity?")) {
      try {
        await authApis().delete(endpoints["activity-detail"](id));
        setActivities((prev) => prev.filter((item) => item.id !== id));
      } catch (err) {
        alert("Delete failed");
      }
    }
  };

  const handleShowBulletinModal = (activityId) => {
    setBulletinFormData({
      title: "",
      content: "",
      duration: "",
      semesterId: "",
      extraActivityId: activityId,
      studentAssistantId: user
        ? { id: user.id, userType: "studentAssistant" }
        : null,
    });
    setShowBulletinModal(true);
  };
  const handleViewStudents = async (activityId) => {
    nav(`/activities/${activityId}/students`);
  };

  const handlePostBulletin = async () => {
    const formattedDate = moment(bulletinFormData.duration).format(
      "YYYY-MM-DD HH:mm:ss"
    );
    const data = {
      ...bulletinFormData,
      semesterId: parseInt(bulletinFormData.semesterId),
      duration: formattedDate,
      bulletinType: "activity",
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

  return (
    <Container>
      <Row className="my-4">
        <Col>
          <h2>Extra Activity Management</h2>
          <Button onClick={handleAddActivity}>+ Add Activity</Button>
        </Col>
      </Row>

      <ActivityTable
        activities={activities}
        onEdit={handleEditActivity}
        onDelete={handleDeleteActivity}
        onShowBulletin={handleShowBulletinModal}
        onViewStudents={handleViewStudents}
      />

      <ActivityForm
        show={showActivityModal}
        onHide={() => setShowActivityModal(false)}
        semesters={semesters}
        terms={terms}
        formData={activityFormData}
        setActivityFormData={setActivityFormData}
        onSave={handleSaveActivity}
      />

      <BulletinForm
        show={showBulletinModal}
        onHide={() => setShowBulletinModal(false)}
        bulletinData={bulletinFormData}
        setBulletinData={setBulletinFormData}
        onPost={handlePostBulletin}
      />
    </Container>
  );
};

export default ActivityManagement;
