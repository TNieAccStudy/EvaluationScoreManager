package com.nhm.repositories;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.UserInfo;
import java.util.Collection;

/**
 *
 * @author admin
 */
public interface UserRepository {
    UserInfo getUserByUsername(String username);
    UserInfo addUser(UserInfo u);
    boolean authenticate(String username, String password);
    UserInfo getUserById(int id);
    Collection<ActivityRegistry> getRegistriesByUserId(int userId);
    Collection<ActivityConfirmedAttendance> getAttendsByUserId(int userId);
    Collection<MissingActivity> getMissingsByUserId(int userId);
}