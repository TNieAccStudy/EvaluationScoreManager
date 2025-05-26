package com.nhm.repositories;
import com.nhm.dto.CSVAttendancesData;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.Student;
import com.nhm.pojo.UserInfo;
import java.util.Collection;

/**
 *
 * @author admin
 */
public interface UserRepository {
    UserInfo getUserByUsername(String username);
    UserInfo addUser(UserInfo u);
    UserInfo updateUser(UserInfo u);
    boolean authenticate(String username, String password);
    UserInfo getUserById(int id);
    Student getStudentByMssv(String mssv);
    Collection<UserInfo> getUsers();
    Collection<ActivityRegistry> getRegistriesByUserId(int userId);
    Collection<ActivityConfirmedAttendance> getAttendsByUserId(int userId);
    Collection<MissingActivity> getMissingsByUserId(int userId);
    <T extends UserInfo> Collection<T> getUsers(Class<T> type);
    int getEvaluationScoreByUserIdOfSemesterId(int studentId, int semesterId);
    int getTotalEvaluationScoreByUserId(int studentId);
    Collection<ActivityConfirmedAttendance> loadAttendanceFromCSVAttendanceData(CSVAttendancesData csvAttendanceData);
}