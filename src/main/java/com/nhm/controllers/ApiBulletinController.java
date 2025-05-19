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
import com.nhm.services.BulletinService;
import com.nhm.viewconfigs.DisplayView;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiBulletinController {
    
    @Autowired
    private BulletinService bulletinService;
    
    @PostMapping("/bulletins")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Bulletin> create(@RequestBody Bulletin bulletin) {
        Bulletin saved = this.bulletinService.addOrUpdate(bulletin);
        System.out.println("Saved Bulletin: " + saved);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    
    @PatchMapping("/bulletins/{bulletinId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Bulletin> patialUpdate(@PathVariable("bulletinId") int bulletinId,@RequestBody Bulletin bulletin) {
        return new ResponseEntity<>(this.bulletinService.addOrUpdate(bulletin), HttpStatus.OK);
    }
    
    @GetMapping("/bulletins")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<Bulletin>> list() {
        List<Bulletin> bulletins = this.bulletinService.getBulletins().stream().collect(Collectors.toList());
        return new ResponseEntity<>(bulletins, HttpStatus.OK);
    }
    
    @DeleteMapping("/bulletins/{bulletinId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("bulletinId") int bulletinId) {
        this.bulletinService.deleteBulletinById(bulletinId);
    }
    
    @GetMapping("/bulletins/{bulletinId}/interactions")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<Interaction>> getInteractionsByBulletinId(@PathVariable("bulletinId") int bulletinId) {
        List<Interaction> interactions = this.bulletinService.getInteractionsByBulletinId(bulletinId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(interactions, HttpStatus.OK);
    }
    
    @GetMapping("/bulletins/{bulletinId}/missings")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<MissingActivity>> getMissingsByBulletinId(@PathVariable("bulletinId") int bulletinId) {
        List<MissingActivity> missings = this.bulletinService.getMissingActivityBySummaryBulletinId(bulletinId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(missings, HttpStatus.OK);
    }
    
    @GetMapping("/bulletins/{bulletinId}/activity")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ExtraActivity> getActivityByBulletinId(@PathVariable("bulletinId") int bulletinId) {
        return new ResponseEntity<>(this.bulletinService.getActivityByBulletinId(bulletinId), HttpStatus.OK);
    }
    
    @GetMapping("/bulletins/{bulletinId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Bulletin> retrieve(@PathVariable("bulletinId") int bulletinId) {
        return new ResponseEntity<>(this.bulletinService.getBulletinById(bulletinId), HttpStatus.OK);
    }
    
}
