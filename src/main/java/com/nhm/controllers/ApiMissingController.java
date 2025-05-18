/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.MissingActivity;
import com.nhm.services.MissingActivityService;
import com.nhm.viewconfigs.DisplayView;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiMissingController {
    
    @Autowired
    private MissingActivityService missingService;
    
    @PostMapping("/missings")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<MissingActivity> create(@RequestBody MissingActivity missing) {
        return new ResponseEntity<>(this.missingService.addOrUpdate(missing), HttpStatus.CREATED);
    }
    
    @PatchMapping("/missings/{missingId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<MissingActivity> patialUpdate(@PathVariable("missingId") int missingId, @RequestBody MissingActivity missing) {
        return new ResponseEntity<>(this.missingService.addOrUpdate(missing), HttpStatus.OK);
    }
    
    @GetMapping("/missings/{missingId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<MissingActivity> retrieve(@PathVariable("missingId") int missingId) {
        return new ResponseEntity<>(this.missingService.getMissingById(missingId), HttpStatus.OK);
    }
    
}
