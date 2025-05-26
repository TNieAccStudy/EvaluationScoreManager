/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.UserInfo;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService {
    UserInfo getUserByUsername(String username);
    UserInfo addUser(UserInfo user, MultipartFile avatar) throws IOException, Exception;
    UserInfo updateUser(UserInfo u);
    boolean authenticate(String username, String password);
    UserInfo getUserById(int id);
    Collection<ActivityRegistry> getRegistriesByUserId(int userId);
    Collection<ActivityConfirmedAttendance> getAttendsByUserId(int userId);
    Collection<MissingActivity> getMissingsByUserId(int userId);
    <T extends UserInfo> Collection<T> getUsers(Class<T> type);
}
