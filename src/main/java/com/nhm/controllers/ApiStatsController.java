/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.nhm.dto.StatsDTO;
import com.nhm.repositories.StatsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/stats")
@CrossOrigin
public class ApiStatsController {
    
    @Autowired
    private StatsRepository statsRepo;
    
    @GetMapping("class")
    public ResponseEntity<?> getStatsForClass(@RequestParam("semester") int semesterId) {
        List<StatsDTO> classStats = this.statsRepo.classStatsWithSemeseter(semesterId).stream().collect(Collectors.toList());
        
        return new ResponseEntity(classStats, HttpStatus.OK);
    }
    
    @GetMapping("department")
    public ResponseEntity<?> getStatsForDepartment(@RequestParam("semester") int semesterId) {
        List<StatsDTO> departmentStats = this.statsRepo.departmentStatsWithSemeseter(semesterId).stream().collect(Collectors.toList());
        
        return new ResponseEntity(departmentStats, HttpStatus.OK);
    }
    
    @GetMapping("achievement")
    public ResponseEntity<?> getStatsForAchievement() {
        List<StatsDTO> achievementStats = this.statsRepo.achievementStats().stream().collect(Collectors.toList());
        
        return new ResponseEntity(achievementStats, HttpStatus.OK);
    }
    
}
