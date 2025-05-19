/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.services.ActivityConfirmedAttendanceService;
import com.nhm.viewconfigs.DisplayView;
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
@RequestMapping("/api")
@CrossOrigin
public class ApiConfirmedAttendanceController {
    
    @Autowired
    private ActivityConfirmedAttendanceService attendanceService;
    
    @PostMapping("/attendances")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityConfirmedAttendance> create(@RequestBody ActivityConfirmedAttendance attendance) {
        return new ResponseEntity<>(this.attendanceService.addOrUpdate(attendance), HttpStatus.CREATED);
    }
    
    @PatchMapping("/attendances/{attendanceId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityConfirmedAttendance> patialUpdate(@PathVariable("attendanceId") int attendanceId, @RequestBody ActivityConfirmedAttendance attendance) {
        return new ResponseEntity<>(this.attendanceService.addOrUpdate(attendance), HttpStatus.OK);
    }
    
    @GetMapping("/attendances/{attendanceId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ActivityConfirmedAttendance> retrieve(@PathVariable("attendanceId") int attendanceId) {
        return new ResponseEntity<>(this.attendanceService.getAttendanceById(attendanceId), HttpStatus.OK);
    }
    
}
