/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.services.ActivityConfirmedAttendanceService;
import com.nhm.viewconfigs.DisplayView;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/attendances")
@CrossOrigin
public class ApiConfirmedAttendanceController {

    @Autowired
    private ActivityConfirmedAttendanceService attendanceService;

    @PostMapping(path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityConfirmedAttendance> create(@RequestPart("data") ActivityConfirmedAttendance attendance, @RequestPart("proofPicture") MultipartFile proofPicture) throws Exception {
        return new ResponseEntity<>(this.attendanceService.addOrUpdate(attendance, proofPicture), HttpStatus.CREATED);
    }

    @GetMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<ActivityConfirmedAttendance>> list() {
        return new ResponseEntity<>(this.attendanceService.getAttendances().stream().collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping(path = "/{attendanceId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityConfirmedAttendance> patialUpdate(@PathVariable("attendanceId") int attendanceId, @RequestBody ActivityConfirmedAttendance attendance) throws Exception {
        return new ResponseEntity<>(this.attendanceService.addOrUpdate(attendance, null), HttpStatus.OK);
    }


    @GetMapping("/{attendanceId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ActivityConfirmedAttendance> retrieve(@PathVariable("attendanceId") int attendanceId) {
        return new ResponseEntity<>(this.attendanceService.getAttendanceById(attendanceId), HttpStatus.OK);
    }

}
