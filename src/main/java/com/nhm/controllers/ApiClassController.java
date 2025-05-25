/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Classe;
import com.nhm.pojo.Student;
import com.nhm.services.ClassService;
import com.nhm.viewconfigs.DisplayView;
import java.util.Collection;
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
@RequestMapping("/api/classes")
@CrossOrigin
public class ApiClassController {
    
    @Autowired
    private ClassService classService;
    
    @PostMapping
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Classe> create(@RequestBody Classe classe) {
        return new ResponseEntity<>(this.classService.addOrUpdate(classe), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{classId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Classe> updatePatial(@PathVariable("classId") int classId , @RequestBody Classe classe) {
        return new ResponseEntity<>(this.classService.addOrUpdate(classe), HttpStatus.OK);
    }
    
    @GetMapping("/{classId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Classe> retrieve(@PathVariable("classId") int classId) {
        return new ResponseEntity<>(this.classService.getClassById(classId), HttpStatus.OK);
    }
    
    @GetMapping
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<Collection<Classe>> list() {
        return new ResponseEntity<>(this.classService.getClasses(), HttpStatus.OK);
    }
    
    @GetMapping("/{classId}/students")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<Collection<Student>> getStudentsByClassId(@PathVariable("classId") int classeId) {
        return new ResponseEntity<>(this.classService.getStudentsByClassId(classeId), HttpStatus.OK);
    }
    
}
