/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.StudentAssistant;
import com.nhm.pojo.UserInfo;
import com.nhm.services.BulletinService;
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
@RequestMapping("/api/bulletins")
@CrossOrigin
public class ApiBulletinController {
    
    @Autowired
    private BulletinService bulletinService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<?> create(@RequestBody Bulletin bulletin, Principal principal) {
        try {
            UserInfo u = UserInfo.getUserByUsernameWithInstance(principal.getName(), userService, StudentAssistant.class);
            bulletin.setStudentAssistantId((StudentAssistant)u);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.entry("error", "your account doesn't enough permission."));
        }
        
        return new ResponseEntity<>(this.bulletinService.addOrUpdate(bulletin), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{bulletinId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Bulletin> patialUpdate(@PathVariable("bulletinId") int bulletinId,@RequestBody Bulletin bulletin) {
        return new ResponseEntity<>(this.bulletinService.addOrUpdate(bulletin), HttpStatus.OK);
    }
    
    @GetMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<Bulletin>> list(@RequestParam Map<String, String> params) {
        String bulletinType = params.get("type");
        Class<? extends Bulletin> bulletinSubType = Bulletin.getSubClassByString(bulletinType);
        
        List<Bulletin> bulletins = this.bulletinService.getBulletins(params, bulletinSubType).stream().collect(Collectors.toList());
        return new ResponseEntity<>(bulletins, HttpStatus.OK);
    }
    
    @DeleteMapping("/{bulletinId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("bulletinId") int bulletinId) {
        this.bulletinService.deleteBulletinById(bulletinId);
    }
    
    @GetMapping("/{bulletinId}/interactions")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<Interaction>> getInteractionsByBulletinId(@PathVariable("bulletinId") int bulletinId, @RequestParam("type") String interactionType) {
        Class<? extends Interaction> interactionSubType = Interaction.getSubClassByString(interactionType);
        
        List<Interaction> interactions = this.bulletinService.getInteractionsByBulletinId(bulletinId, interactionSubType).stream().collect(Collectors.toList());
        return new ResponseEntity<>(interactions, HttpStatus.OK);
    }
    
    @GetMapping("/{bulletinId}/missings")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<MissingActivity>> getMissingsByBulletinId(@PathVariable("bulletinId") int bulletinId) {
        List<MissingActivity> missings = this.bulletinService.getMissingActivityBySummaryBulletinId(bulletinId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(missings, HttpStatus.OK);
    }
    
    @GetMapping("/{bulletinId}/activity")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ExtraActivity> getActivityByBulletinId(@PathVariable("bulletinId") int bulletinId) {
        return new ResponseEntity<>(this.bulletinService.getActivityByBulletinId(bulletinId), HttpStatus.OK);
    }
    
    @GetMapping("/{bulletinId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Bulletin> retrieve(@PathVariable("bulletinId") int bulletinId) {
        return new ResponseEntity<>(this.bulletinService.getBulletinById(bulletinId), HttpStatus.OK);
    }
    
}
