/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.services.ActivityRegistryService;
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
public class ApiRegistryController {
    
    @Autowired
    private ActivityRegistryService registryService;
    
    @PostMapping("/registries")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityRegistry> create(@RequestBody ActivityRegistry registry) {
        return new ResponseEntity<>(this.registryService.addOrUpdate(registry), HttpStatus.CREATED);
    }
    
    @PatchMapping("/registries/{registryId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<ActivityRegistry> updatePatial(@PathVariable("registryId") int registryId, @RequestBody ActivityRegistry registry) {
        return new ResponseEntity<>(this.registryService.addOrUpdate(registry), HttpStatus.OK);
    }
    
    @GetMapping("/registries/{registryId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<ActivityRegistry> retrieve(@PathVariable("registryId") int registryId) {
        return new ResponseEntity<>(this.registryService.getRegistryById(registryId), HttpStatus.OK);
    }
    
}
