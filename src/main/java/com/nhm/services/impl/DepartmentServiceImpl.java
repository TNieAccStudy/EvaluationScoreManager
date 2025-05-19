/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.Classe;
import com.nhm.pojo.Department;
import com.nhm.repositories.DepartmentRepository;
import com.nhm.services.DepartmentService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepo;

    @Override
    public Department addOrUpdate(Department department) {
        return this.departmentRepo.addOrUpdate(department);
    }

    @Override
    public Department getDepartmentById(int id) {
        return this.departmentRepo.getDepartmentById(id);
    }

    @Override
    public Collection<Department> getDepartments() {
        return this.departmentRepo.getDepartments();
    }

    @Override
    public Collection<Classe> getClassesByDepartmentId(int id) {
        return this.departmentRepo.getClassesByDepartmentId(id);
    }
    
}
