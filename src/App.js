import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import Header from "./components/layouts/Header";
import Footer from "./components/layouts/Footer";
import Home from "./components/Home";
import Register from "./components/Register";
import "bootstrap/dist/css/bootstrap.min.css";
import { Container } from "react-bootstrap";
import Login from "./components/Login";
import { MyDispatcherContext, MyUserContext } from "./configs/MyContexts";
import { useReducer, useEffect } from "react";
import MyUserReducer from "./reducers/MyUserReducer";
import Chat from "./components/ChatMessage/Chat";
import ChatWithStudentAssistant from "./components/ChatMessage/ChatWithStudentAssistant";
import ChatDetail from "./components/ChatMessage/ChatDetail";
import ActivityManagement from "./components/Activity/ActivityManagement";
import StudentList from "./components/Student/StudentList";
import cookie from "react-cookies";
import { authApis, endpoints } from "./configs/Apis";
import Summary from "./components/Summary/Summary";
import StudentManagement from "./components/Student/StudentManagement";
import Bulletin from "./components/Bulletin/Bulletin";
import BulletinDetail from "./components/Bulletin/BulletinDetail";
import MissingActivity from "./components/MissingActivity/MissingActivity";
import ActivityRegistries from "./components/ActivityRegistries/ActivityRegistries";
import "moment/locale/vi";
import ActivityAttendances from "./components/ActivityAttendances/ActivityAttendances";

const PrivateRoute = ({ children }) => {
  const token = cookie.load("token");
  return token ? children : <Navigate to="/login" />;
};

const App = () => {
  const [user, dispatch] = useReducer(MyUserReducer, null);

  const getUser = async () => {
    let u = await authApis().get(endpoints["current-user"]);
    console.info(u.data);
    return u.data;
  };
  useEffect(() => {
    const checkLogin = async () => {
      const token = cookie.load("token");
      console.log("token", token);
      if (token) {
        const userData = await getUser();
        if (userData) {
          dispatch({
            type: "login",
            payload: userData,
          });
        }
      }
    };

    checkLogin();
  }, []);

  return (
    <MyUserContext.Provider value={user}>
      <MyDispatcherContext.Provider value={dispatch}>
        <BrowserRouter>
          <Header />

          <Container>
            <Routes>
              <Route
                path="/"
                element={
                  <PrivateRoute>
                    <Bulletin />
                  </PrivateRoute>
                }
              />
              <Route path="/register" element={<Register />} />
              <Route path="/login" element={<Login />} />
              <Route
                path="/bulletins"
                element={
                  <PrivateRoute>
                    <Bulletin />
                  </PrivateRoute>
                }
              />
              <Route
                path="/bulletins/:bulletinId"
                element={
                  <PrivateRoute>
                    <BulletinDetail />
                  </PrivateRoute>
                }
              />
              <Route
                path="/bulletins/:bulletinId/summary"
                element={
                  <PrivateRoute>
                    <Summary />
                  </PrivateRoute>
                }
              />

              <Route
                path="/activities"
                element={
                  <PrivateRoute>
                    <ActivityManagement />
                  </PrivateRoute>
                }
              />
              <Route
                path="/activities-registries"
                element={
                  <PrivateRoute>
                    <ActivityRegistries />
                  </PrivateRoute>
                }
              />

              <Route
                path="/attendances-activities"
                element={
                  <PrivateRoute>
                    <ActivityAttendances />
                  </PrivateRoute>
                }
              />
              <Route
                path="/activities/:activityId/students"
                element={
                  <PrivateRoute>
                    <StudentList />
                  </PrivateRoute>
                }
              />
              <Route
                path="/missings-activities/"
                element={
                  <PrivateRoute>
                    <MissingActivity />
                  </PrivateRoute>
                }
              />
              <Route
                path="/message"
                element={
                  <PrivateRoute>
                    <ChatWithStudentAssistant />
                  </PrivateRoute>
                }
              />
              <Route
                path="/students"
                element={
                  <PrivateRoute>
                    <StudentManagement />
                  </PrivateRoute>
                }
              />
              <Route
                path="/chat/:username"
                element={
                  <PrivateRoute>
                    <ChatDetail />
                  </PrivateRoute>
                }
              />
              <Route
                path="/chat"
                element={
                  <PrivateRoute>
                    <Chat />
                  </PrivateRoute>
                }
              />
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyDispatcherContext.Provider>
    </MyUserContext.Provider>
  );
};

export default App;
