/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.Student;
import com.nhm.pojo.StudentAssistant;
import com.nhm.pojo.UserInfo;
import com.nhm.services.MissingActivityService;
import com.nhm.services.UserService;
import com.nhm.viewconfigs.DisplayView;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/missings")
@CrossOrigin
public class ApiMissingController {
    
    @Autowired
    private MissingActivityService missingService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping(path="",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> create(@RequestPart("data") MissingActivity missing, @RequestPart("proofPicture") MultipartFile proofPicture, Principal principal) throws Exception {
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, Student.class);
            missing.setStudentId((Student)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(this.missingService.addOrUpdate(missing, proofPicture), HttpStatus.CREATED);
    }
    
    @PatchMapping(path="/{missingId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<MissingActivity> patialUpdate(@PathVariable("missingId") int missingId, @RequestPart("data") MissingActivity missing, @RequestPart("proofPicture") MultipartFile proofPicture) throws Exception {
        return new ResponseEntity<>(this.missingService.addOrUpdate(missing, proofPicture), HttpStatus.OK);
    }
    
    @PatchMapping("/{missingId}/response")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> responseMissing(@PathVariable("missingId") int missingId, Principal principal) throws Exception {
        MissingActivity missing = this.missingService.getMissingById(missingId);
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, StudentAssistant.class);
            missing.setStudentAssistantId((StudentAssistant)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(this.missingService.addOrUpdate(missing, null), HttpStatus.OK);
    }
    
    @GetMapping("/{missingId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<MissingActivity> retrieve(@PathVariable("missingId") int missingId) {
        return new ResponseEntity<>(this.missingService.getMissingById(missingId), HttpStatus.OK);
    }
    
}
