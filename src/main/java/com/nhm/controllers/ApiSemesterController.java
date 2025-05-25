/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Semester;
import com.nhm.services.SemesterService;
import com.nhm.viewconfigs.DisplayView;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/semesters")
@CrossOrigin
public class ApiSemesterController {
    
    @Autowired
    private SemesterService semesterService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Semester> create(@RequestBody Semester semester) {
        return new ResponseEntity<>(this.semesterService.addOrUpdate(semester), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{semesterId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Semester> patialUpdate(@PathVariable("semesterId") int semesterId, @RequestBody Semester semester) {
        return new ResponseEntity<>(this.semesterService.addOrUpdate(semester), HttpStatus.OK);
    }
    
    @GetMapping
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<Semester>> list() {
        List<Semester> activities = this.semesterService.getSemesters().stream().collect(Collectors.toList());
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    
    @GetMapping("/{semesterId}/activities")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<ExtraActivity>> getActivitesBySemesterId(@PathVariable("semesterId") int semesterId) {
        List<ExtraActivity> activities = this.semesterService.getActivitesByTermId(semesterId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    
    @GetMapping("/{semesterId}/bulletins")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<Bulletin>> getBulletinsBySemesterId(@PathVariable("semesterId") int semesterId, @RequestParam("type") String bulletinType) {
        Class<? extends Bulletin> bulletinSubType = Bulletin.getSubClassByString(bulletinType);
        List<Bulletin> activities = this.semesterService.getBulletinsByTermId(semesterId, bulletinSubType).stream().collect(Collectors.toList());
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    
    @GetMapping("/{semesterId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Semester> retrieve(@PathVariable("semesterId") int semesterId) {
        return new ResponseEntity<>(this.semesterService.getSemesterById(semesterId), HttpStatus.OK);
    }
    
}
