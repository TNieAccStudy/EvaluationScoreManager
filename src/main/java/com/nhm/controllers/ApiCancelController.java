/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.CancelRequirement;
import com.nhm.services.CancelRequirementService;
import com.nhm.viewconfigs.DisplayView;
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
public class ApiCancelController {
    
    @Autowired
    private CancelRequirementService cancelService;
    
    @PostMapping("/cancels")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<CancelRequirement> create(@RequestBody CancelRequirement cancel) {
        return new ResponseEntity<>(this.cancelService.addOrUpdate(cancel), HttpStatus.CREATED);
    }
    
    @PatchMapping("/cancels/{cancelId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<CancelRequirement> create(@PathVariable("cancelId") int cancelId , @RequestBody CancelRequirement cancel) {
        return new ResponseEntity<>(this.cancelService.addOrUpdate(cancel), HttpStatus.OK);
    }
    
    @DeleteMapping("/cancels/{cancelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable("cancelId") int cancelId) {
        this.cancelService.deleteCancelById(cancelId);
    }
    
    @GetMapping("/cancels/{cancelId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<CancelRequirement> retrive(@PathVariable("cancelId") int cancelId) {
        return new ResponseEntity<>(this.cancelService.getCancelById(cancelId), HttpStatus.OK);
    }
    
}
