/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhm.pojo.UserInfo;
import com.nhm.services.UserService;
import com.nhm.utils.JwtUtils;
import com.nhm.viewconfigs.DisplayView;
import java.security.Principal;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiUserController {

    @Autowired
    private UserService userDetailsService;

    @PostMapping(path = "/users", 
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView({
        DisplayView.Internal.class
    })
    public ResponseEntity<UserInfo> create(@RequestPart("data") UserInfo userData, @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws JsonProcessingException {
        System.out.println("user data : " + userData);
        
        return new ResponseEntity<>(this.userDetailsService.addUser(userData, avatar), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @JsonView({
        DisplayView.Internal.class
    })
    public ResponseEntity<?> login(@RequestBody UserInfo u) {

        if (this.userDetailsService.authenticate(u.getUsername(), u.getPassword())) {
            try {
                String token = JwtUtils.generateToken(u.getUsername());
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @JsonView({
        DisplayView.Internal.class
    })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<UserInfo> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userDetailsService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }
    
    @GetMapping("/users/{userId}")
    @JsonView({
        DisplayView.Internal.class
    })
    public ResponseEntity<UserInfo> getUserDetail(@PathVariable("userId") int userId) {
        return new ResponseEntity<>(this.userDetailsService.getUserById(userId), HttpStatus.OK);
    }
    
}
