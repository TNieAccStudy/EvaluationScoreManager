/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.nhm.pojo.Classe;
import com.nhm.pojo.Department;
import com.nhm.services.DepartmentService;
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
@RequestMapping("/api")
@CrossOrigin
public class ApiDepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    @PostMapping("/departments")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Department> create(@RequestBody Department department) {
        return new ResponseEntity<>(this.departmentService.addOrUpdate(department), HttpStatus.CREATED);
    }
    
    @PatchMapping("/departments/{departmentId}")
    @JsonView(DisplayView.Public.class)
    public ResponseEntity<Department> updatePatial(@PathVariable("departmentId") int departmentId , @RequestBody Department department) {
        return new ResponseEntity<>(this.departmentService.addOrUpdate(department), HttpStatus.OK);
    }
    
    @GetMapping("/departments/{departmentId}")
    @JsonView(DisplayView.Internal.class)
    public ResponseEntity<Department> retrive(@PathVariable("departmentId") int departmentId) {
        return new ResponseEntity<>(this.departmentService.getDepartmentById(departmentId), HttpStatus.OK);
    }
    
    @GetMapping("/departments")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<Collection<Department>> list() {
        return new ResponseEntity<>(this.departmentService.getDepartments(), HttpStatus.OK);
    }
    
    @GetMapping("/departments/{departmentId}/classes")
    @JsonView(DisplayView.Simplify.class)
    public ResponseEntity<Collection<Classe>> getClassesByDepartmentId(@PathVariable("departmentId") int departmentId) {
        return new ResponseEntity<>(this.departmentService.getClassesByDepartmentId(departmentId), HttpStatus.OK);
    }
    
}
