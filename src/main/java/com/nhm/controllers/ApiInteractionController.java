/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import com.nhm.services.InteractionService;
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
public class ApiInteractionController {
    
    @Autowired
    private InteractionService interactionService;
    
    @PostMapping("/interactions")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Interaction> create(@RequestBody Interaction interaction) {
        return new ResponseEntity<>(interactionService.addOrUpdate(interaction), HttpStatus.CREATED);
    }
    
    @PatchMapping("/interactions/{interactionId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Interaction> create(@PathVariable("interactionId") int interactionId,@RequestBody Interaction interaction) {
        return new ResponseEntity<>(interactionService.addOrUpdate(interaction), HttpStatus.OK);
    }
    
    @DeleteMapping("/interactions/{interactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("interactionId") int interactionId) {
        this.interactionService.deleteInteractionById(interactionId);
    }
    
    @GetMapping("/interactions/{interactionId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Interaction> retrive(@PathVariable("interactionId") int interactionId,@RequestBody Interaction interaction) {
        return new ResponseEntity<>(interactionService.getInteractionById(interactionId), HttpStatus.OK);
    }
    
}
