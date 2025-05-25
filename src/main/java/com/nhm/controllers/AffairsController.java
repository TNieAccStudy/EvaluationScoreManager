/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.CancelRequirement;
import com.nhm.pojo.UserInfo;
import com.nhm.services.BulletinService;
import com.nhm.services.CancelRequirementService;
import com.nhm.services.ExtraActivityService;
import com.nhm.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author GIGABYTE
 */
@Controller
@RequestMapping("/affairs")
public class AffairsController {
    
    @Autowired
    private BulletinService bulletinService;
    
    @Autowired
    private ExtraActivityService activityService;
    
    @Autowired
    private CancelRequirementService cancelService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(path = {
        "/index",
        ""
    })
    public String affairIndex() {
        return "affairs/index.html";
    }
    
    @RequestMapping("/bulletins")
    public String bulletinList(Model model) {
        
        model.addAttribute("bulletins", bulletinService.getBulletins(Map.of(), Bulletin.class));
        
        return "affairs/bulletin-list.html";
    }
    
    @RequestMapping("/bulletins/{bulletinId}")
    public String bulletinDetail(Model model, @PathVariable("bulletinId") int bulletinId) {
        
        model.addAttribute("bulletin", bulletinService.getBulletinById(bulletinId));
        
        return "affairs/details/bulletin-detail.html";
    }
    
    @RequestMapping("/activities")
    public String activityList(Model model) {
        
        model.addAttribute("activities", activityService.getActivities(Map.of()));
        
        return "affairs/activity-list.html";
    }
    
    @RequestMapping("/cancels")
    public String cancelList(Model model) {
        
        model.addAttribute("cancels", cancelService.getCancels(CancelRequirement.class));
        
        return "affairs/cancel-list.html";
    }
    
    @RequestMapping("/accounts")
    public String userList(Model model) {
        
        model.addAttribute("users", userService.getUsers(UserInfo.class));
        
        return "affairs/account-list.html";
    }
    
}
