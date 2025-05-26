/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExecuteStatus;
import com.nhm.pojo.Student;
import com.nhm.pojo.StudentAssistant;
import com.nhm.services.BulletinService;
import com.nhm.services.ClassService;
import com.nhm.services.ExtraActivityService;
import com.nhm.services.SemesterService;
import com.nhm.services.TermService;
import com.nhm.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 *
 * @author GIGABYTE
 */
@Controller
@ControllerAdvice
public class AffairsCommonsController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private SemesterService semesterService;
    
    @Autowired
    private TermService termService;
    
    @Autowired
    private BulletinService bulletinService;
    
    @Autowired
    private ExtraActivityService activityService;
    
    @Autowired
    private ClassService classService;
    
    @ModelAttribute
    public void commonsResponse(Model model) {
        model.addAttribute("assistants", userService.getUsers(StudentAssistant.class));
        model.addAttribute("semesters", semesterService.getSemesters());
        model.addAttribute("terms", termService.getTerms());
        model.addAttribute("bulletins", bulletinService.getBulletins(Map.of(), Bulletin.class));
        model.addAttribute("activities", activityService.getActivities(Map.of()));
        model.addAttribute("classes", classService.getClasses());
        
        model.addAttribute("bulletinStates", Bulletin.BulletinState.values());
        model.addAttribute("executeStatuses", ExecuteStatus.values());
        model.addAttribute("achivements", Student.Achievement.values());
        
    }
}
