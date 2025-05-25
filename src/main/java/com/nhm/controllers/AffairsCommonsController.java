/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.StudentAssistant;
import com.nhm.services.SemesterService;
import com.nhm.services.UserService;
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
    
    @ModelAttribute
    public void commonsResponse(Model model) {
        model.addAttribute("assistants", userService.getUsers(StudentAssistant.class));
        model.addAttribute("semesters", semesterService.getSemesters());
        model.addAttribute("bulletinStates", Bulletin.BulletinState.values());
    }
}
