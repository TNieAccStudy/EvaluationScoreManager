package com.nhm.repositories;
import com.nhm.pojo.UserInfo;

/**
 *
 * @author admin
 */
public interface UserRepository {
    UserInfo getUserByUsername(String username);
    UserInfo addUser(UserInfo u);
    boolean authenticate(String username, String password);
    UserInfo getUserById(int id);
}