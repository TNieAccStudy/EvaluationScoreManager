/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.Student;
import com.nhm.pojo.UserInfo;
import com.nhm.services.InteractionService;
import com.nhm.services.UserService;
import com.nhm.viewconfigs.DisplayView;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/interactions")
@CrossOrigin
public class ApiInteractionController {
    
    @Autowired
    private InteractionService interactionService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> create(@RequestBody Interaction interaction, Principal principal) {
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, Student.class);
            interaction.setStudentId((Student)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        return new ResponseEntity<>(interactionService.addOrUpdate(interaction), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{interactionId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Interaction> patialUpdate(@PathVariable("interactionId") int interactionId,@RequestBody Interaction interaction) {
        return new ResponseEntity<>(interactionService.addOrUpdate(interaction), HttpStatus.OK);
    }
    
    @DeleteMapping("/{interactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("interactionId") int interactionId) {
        this.interactionService.deleteInteractionById(interactionId);
    }
    
    @GetMapping("/{interactionId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Interaction> retrieve(@PathVariable("interactionId") int interactionId,@RequestBody Interaction interaction) {
        return new ResponseEntity<>(interactionService.getInteractionById(interactionId), HttpStatus.OK);
    }
    
}
