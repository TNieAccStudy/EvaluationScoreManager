/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.StudentAssistant;
import com.nhm.pojo.UserInfo;
import com.nhm.services.ExtraActivityService;
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
@RequestMapping("/api/activities")
@CrossOrigin
public class ApiActivityController {
    
    @Autowired
    private ExtraActivityService activityService;
    @Autowired
    private UserService userService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> create(@RequestBody ExtraActivity activity, Principal principal) {
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, StudentAssistant.class);
            activity.setStudentAssistantId((StudentAssistant)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(this.activityService.addOrUpdate(activity), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{activityId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ExtraActivity> patialUpdate(@PathVariable("activityId") int activityId, @RequestBody ExtraActivity activity) {
        return new ResponseEntity<>(this.activityService.addOrUpdate(activity), HttpStatus.OK);
    }
    
    @GetMapping
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<ExtraActivity>> list(@RequestParam Map<String, String> params) {
        List<ExtraActivity> activities = this.activityService.getActivities(params).stream().collect(Collectors.toList());
        
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    
    @GetMapping("/{activityId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ExtraActivity> retrieve(@PathVariable("activityId") int activityId) {
        return new ResponseEntity<>(this.activityService.getActivityById(activityId), HttpStatus.OK);
    }
    
    @GetMapping("/activities/{activityId}/attendances")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<ActivityConfirmedAttendance>> getAttendancesByActivityId(@PathVariable("activityId") int activityId) {
        List<ActivityConfirmedAttendance> attendances = this.activityService.getAttendancesByActivityId(activityId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }
    
    @GetMapping("/{activityId}/registries")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<ActivityRegistry>> getRegiestriesByActivityId(@PathVariable("activityId") int activityId) {
        List<ActivityRegistry> registries = this.activityService.getResigtriesByActivityId(activityId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(registries, HttpStatus.OK);
    }
    
    @GetMapping("/{activityId}/missings")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<MissingActivity>> getMissingsByActivityId(@PathVariable("activityId") int activityId) {
        List<MissingActivity> missings = this.activityService.getMissingsByActivityId(activityId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(missings, HttpStatus.OK);
    }
    
    @DeleteMapping("/activities/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("activityId") int activityId) {
        this.activityService.deleteActivityById(activityId);
    }
    
}
