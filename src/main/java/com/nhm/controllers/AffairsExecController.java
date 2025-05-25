/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Bulletin;
import com.nhm.services.BulletinService;
import com.nhm.services.CancelRequirementService;
import com.nhm.services.ExtraActivityService;
import com.nhm.services.UserService;
import com.nhm.viewconfigs.DisplayView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author GIGABYTE
 */
@Controller
@RequestMapping("/affairs/exec")
public class AffairsExecController {
    @Autowired
    private BulletinService bulletinService;
    
    @Autowired
    private ExtraActivityService activityService;
    
    @Autowired
    private CancelRequirementService cancelService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/bulletins")
    @JsonView(DisplayView.Internal.class)
    public String bulletinPartialUpdate(@ModelAttribute("bulletin") Bulletin bulletinData) {
        Bulletin bulletin = bulletinService.getBulletinById(bulletinData.getId().intValue());
        BeanUtils.copyProperties(bulletinData, bulletin);
        bulletinService.addOrUpdate(bulletin);
        
        return "redirect:/affairs/bulletins";
    }
    
}
