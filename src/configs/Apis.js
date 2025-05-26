import axios from "axios";
import cookie from "react-cookies";

const BASE_URL = "http://localhost:8080/ManageTrainingPoints/api/";

export const endpoints = {
  register: "/users",
  login: "/login",
  "current-user": "/secure/profile",

  bulletins: "/bulletins",
  "bulletins-detail": (bulletinId) => `/bulletins/${bulletinId}`,
  "bulletins-interactions": (bulletinId) =>
    `/bulletins/${bulletinId}/interactions`,

  activities: "/activities",
  "activities-registries": (activityId) =>
    `/activities/${activityId}/registries`,
  "activities-attendances": (activityId) =>
    `/activities/${activityId}/attendances`,
  "activities-registries-of-student": "/students/current-student/registries",
  "activities-attendances-of-student": "/students/current-student/attendances",
  "activities-missings-of-student": "/students/current-student/missings",
  "activity-detail": (activityId) => `/activities/${activityId}`,

  registries: "/registries",
  "registries-detail": (registryId) => `/registries/${registryId}`,

  missings: "/missings",
  "missing-response": (missingId) => `/missings/${missingId}/response`,

  cancels: "/cancels",

  classes: "/classes",
  terms: "/terms",
  "terms-detail": (termId) => `/terms/${termId}`,
  "activities-terms": (termId) => `/terms/${termId}/activities`,

  semesters: "/semesters",

  departments: "/departments",
  "classed-department": (departmentId) =>
    `/departments/${departmentId}/classes`,

  assistants: "/assistants",
  "assistants-upload-csv": `/assistants/current-assistant/postCSVAttendances`,

  students: "/students",
  "evalScores-of-student": "/students/current-student/score",
  "evalScores-of-student-detail": (studentId) => `/students/${studentId}/score`,

  attendances: "attendances",
  "attendances-detail": (attendanceId) => `/attendances/${attendanceId}`,
};

export const authApis = () => {
  return axios.create({
    baseURL: BASE_URL,
    headers: {
      Authorization: `Bearer ${cookie.load("token")}`,
    },
  });
};

export default axios.create({
  baseURL: BASE_URL,
});
