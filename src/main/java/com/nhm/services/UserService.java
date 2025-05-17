/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
public interface UserService extends UserDetailsService {
    UserInfo getUserByUsername(String username);
    UserInfo addUser(UserInfo user, MultipartFile avatar);
    boolean authenticate(String username, String password);
    UserInfo getUserById(int id);
}
