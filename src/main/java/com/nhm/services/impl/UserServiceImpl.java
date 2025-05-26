/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhm.dto.CSVAttendancesData;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.UserInfo;
import com.nhm.repositories.UserRepository;
import com.nhm.services.UserService;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public UserInfo getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getUserRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public UserInfo addUser(UserInfo user, MultipartFile avatar) throws IOException, Exception {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if (user.getUserRole() == null || user.getUserRole().equals("")) {
            user.setUserRole("ROLE_USER");
        }

        String publicId = null;
        if (avatar != null && !avatar.isEmpty()) {
            Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            user.setAvatar(res.get("secure_url").toString());
            publicId = res.get("public_id").toString();
        }

        try {
            user = this.userRepo.addUser(user);
        } catch (Exception e) {
            if (publicId != null) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
            throw new Exception("error when work with database");
        }

        return user;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public UserInfo getUserById(int id) {
        return userRepo.getUserById(id);
    }

    @Override
    public Collection<ActivityRegistry> getRegistriesByUserId(int userId) {
        return this.userRepo.getRegistriesByUserId(userId);
    }

    @Override
    public Collection<ActivityConfirmedAttendance> getAttendsByUserId(int userId) {
        return this.userRepo.getAttendsByUserId(userId);
    }

    @Override
    public Collection<MissingActivity> getMissingsByUserId(int userId) {
        return this.userRepo.getMissingsByUserId(userId);
    }

    @Override
    public <T extends UserInfo> Collection<T> getUsers(Class<T> type) {
        return this.userRepo.getUsers(type);
    }

    @Override
    public UserInfo updateUser(UserInfo u) {
        return this.userRepo.updateUser(u);
    }

    @Override
    public int getEvaluationScoreByUserIdWithSemesterId(int studentId, int semesterId) {
        return this.userRepo.getEvaluationScoreByUserIdOfSemesterId(studentId, semesterId);
    }

    @Override
    public Collection<ActivityConfirmedAttendance> loadAttendanceFromCSVAttendanceData(CSVAttendancesData csvAttendanceData, MultipartFile proofPictureGeneralFile) throws IOException, Exception {
        if (proofPictureGeneralFile != null && !proofPictureGeneralFile.isEmpty()) {
            Map res = cloudinary.uploader().upload(proofPictureGeneralFile.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            csvAttendanceData.setProofPictureGeneral(res.get("secure_url").toString());
            
            return this.userRepo.loadAttendanceFromCSVAttendanceData(csvAttendanceData);
        }
        throw new Exception("data is missing ?");
    }

}
