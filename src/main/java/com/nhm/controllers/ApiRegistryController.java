/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.Student;
import com.nhm.pojo.UserInfo;
import com.nhm.services.ActivityRegistryService;
import com.nhm.services.UserService;
import com.nhm.viewconfigs.DisplayView;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/registries")
@CrossOrigin
public class ApiRegistryController {
    
    @Autowired
    private ActivityRegistryService registryService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> create(@RequestBody ActivityRegistry registry, Principal principal) {
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, Student.class);
            registry.setStudentId((Student)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(this.registryService.addOrUpdate(registry), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{registryId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityRegistry> patialUpdate(@PathVariable("registryId") int registryId, @RequestBody ActivityRegistry registry) {
        return new ResponseEntity<>(this.registryService.addOrUpdate(registry), HttpStatus.OK);
    }
    
    @GetMapping("/{registryId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ActivityRegistry> retrieve(@PathVariable("registryId") int registryId) {
        return new ResponseEntity<>(this.registryService.getRegistryById(registryId), HttpStatus.OK);
    }
    
}
