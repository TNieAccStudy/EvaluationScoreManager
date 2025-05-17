/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Term;
import com.nhm.services.TermService;
import com.nhm.viewconfigs.DisplayView;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author GIGABYTE
 */
@RestController
@RequestMapping("/api/test")
public class TestRestController {
    
    @Autowired
    private TermService termService;
    
    @PostMapping("/terms")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Term> addTerm(@RequestBody Term termData) {
        termService.addOrUpdate(termData);
        
        return new ResponseEntity<>(termData, HttpStatus.CREATED);
    }
    
    @GetMapping("/terms")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<List<Term>> getListTerm() {
        List<Term> terms = termService.getTerms().stream().collect(Collectors.toList());
        
        System.out.println("terms:");
        terms.forEach((t)-> System.out.println("items : " + t.getId()));
        
        return new ResponseEntity<>(terms, HttpStatus.OK);
    }
    
}
