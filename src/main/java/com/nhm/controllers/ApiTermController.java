/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Term;
import com.nhm.services.TermService;
import com.nhm.viewconfigs.DisplayView;
import java.util.List;
import java.util.stream.Collectors;
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
public class ApiTermController {
    
    @Autowired
    private TermService termService;
    
    @PostMapping("/terms")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Term> create(@RequestBody Term term) {
        return new ResponseEntity<>(this.termService.addOrUpdate(term), HttpStatus.CREATED);
    }
    
    @PatchMapping("/terms/{termId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Term> updatePatial(@PathVariable("termId") int termId, @RequestBody Term term) {
        return new ResponseEntity<>(this.termService.addOrUpdate(term), HttpStatus.OK);
    }
    
    @GetMapping("/terms")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<Term>> list() {
        List<Term> terms = this.termService.getTerms().stream().collect(Collectors.toList());
        return new ResponseEntity<>(terms, HttpStatus.OK);
    }
    
    @GetMapping("/terms/{termId}/activities")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<List<ExtraActivity>> getActivitesByTermId(@PathVariable("termId") int termId) {
        List<ExtraActivity> activities = this.termService.getActivitesByTermId(termId).stream().collect(Collectors.toList());
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }
    
    @GetMapping("/terms/{termId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Term> retrieve(@PathVariable("termId") int termId) {
        return new ResponseEntity<>(this.termService.getTermById(termId), HttpStatus.OK);
    }
    
}
