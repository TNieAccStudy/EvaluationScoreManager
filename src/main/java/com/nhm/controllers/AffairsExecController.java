/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.CancelRequirement;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.UserInfo;
import com.nhm.services.BulletinService;
import com.nhm.services.CancelRequirementService;
import com.nhm.services.ExtraActivityService;
import com.nhm.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String bulletinPartialUpdate(@ModelAttribute("bulletin") Bulletin bulletinData) {
        
        Bulletin existing = bulletinService.getBulletinById(bulletinData.getId().intValue());
        BeanUtils.copyProperties(bulletinData, existing, "id", "enable", "createdDate", "updatedDate");
        
        bulletinService.addOrUpdate(existing);

        return "redirect:/affairs/bulletins";
    }
    
    @PostMapping("/activities")
    public String activityPartialUpdate(@ModelAttribute("activity") ExtraActivity extraActvity) {
        
        ExtraActivity existing = activityService.getActivityById(extraActvity.getId().intValue());
        BeanUtils.copyProperties(extraActvity, existing, "id", "enable", "createdDate", "updatedDate");
        
        activityService.addOrUpdate(existing);

        return "redirect:/affairs/activities";
    }
    
    @PostMapping("/cancels")
    public String cancelPartialUpdate(@ModelAttribute("cancel") CancelRequirement cancel) {
        
        CancelRequirement existing = cancelService.getCancelById(cancel.getId().intValue());
        BeanUtils.copyProperties(cancel, existing, "id", "enable", "createdDate", "updatedDate");
        
        cancelService.addOrUpdate(existing);

        return "redirect:/affairs/cancels";
    }
    
    @PostMapping("/users")
    public String cancelPartialUpdate(@ModelAttribute("userInfo") UserInfo userInfo) {
        
        UserInfo existing = userService.getUserById(userInfo.getId().intValue());
        BeanUtils.copyProperties(userInfo, existing, "id", "enable", "createdDate", "updatedDate");
        
        userService.updateUser(existing);

        return "redirect:/affairs/accounts";
    }

}
