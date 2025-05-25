/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.CancelRequirement;
import com.nhm.pojo.StudentAffairsOfficer;
import com.nhm.pojo.StudentAssistant;
import com.nhm.pojo.UserInfo;
import com.nhm.services.CancelRequirementService;
import com.nhm.services.UserService;
import com.nhm.viewconfigs.DisplayView;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController 
@RequestMapping("/api/cancels")
@CrossOrigin
public class ApiCancelController {
    
    @Autowired
    private CancelRequirementService cancelService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> create(@RequestBody CancelRequirement cancel, Principal principal) {
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, StudentAssistant.class);
            cancel.setStudentAssistantId((StudentAssistant)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(this.cancelService.addOrUpdate(cancel), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{cancelId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<CancelRequirement> updatePatial(@PathVariable("cancelId") int cancelId , @RequestBody CancelRequirement cancel) {
        return new ResponseEntity<>(this.cancelService.addOrUpdate(cancel), HttpStatus.OK);
    }
    
    @GetMapping
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<CancelRequirement>> list(@RequestParam("type") String cancelType) {
        Class<? extends CancelRequirement> cancelSubType = CancelRequirement.getSubClassByString(cancelType);
        List<CancelRequirement> cancels = this.cancelService.getCancels(cancelSubType).stream().collect(Collectors.toList());
        return new ResponseEntity<>(cancels, HttpStatus.OK);
    }
    
    @PatchMapping("/{cancelId}/response")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> responseCancel(@PathVariable("cancelId") int cancelId, Principal principal) {
        CancelRequirement cancel = this.cancelService.getCancelById(cancelId);
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, StudentAffairsOfficer.class);
            cancel.setStudentAffairsOfficerId((StudentAffairsOfficer)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(cancel, HttpStatus.OK);
    }
    
    @DeleteMapping("/{cancelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("cancelId") int cancelId) {
        this.cancelService.deleteCancelById(cancelId);
    }
    
    @GetMapping("/{cancelId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<CancelRequirement> retrieve(@PathVariable("cancelId") int cancelId) {
        return new ResponseEntity<>(this.cancelService.getCancelById(cancelId), HttpStatus.OK);
    }
    
}
