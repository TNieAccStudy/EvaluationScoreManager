/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.Classe;
import com.nhm.pojo.Student;
import com.nhm.repositories.ClassRepository;
import com.nhm.services.ClassService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class ClassServiceImpl implements ClassService {
    
    @Autowired
    private ClassRepository classRepo;

    @Override
    public Classe addOrUpdate(Classe classe) {
        return this.classRepo.addOrUpdate(classe);
    }

    @Override
    public Classe getClassById(int id) {
        return this.classRepo.getClassById(id);
    }

    @Override
    public Collection<Classe> getClasses() {
        return this.classRepo.getClasses();
    }

    @Override
    public Collection<Student> getStudentsByClassId(int id) {
        return this.classRepo.getStudentsByClassId(id);
    }
    
}
