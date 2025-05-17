/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Semester;
import com.nhm.repositories.SemesterRepository;
import com.nhm.services.SemesterService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class SemesterServiceImpl implements SemesterService {
    
    @Autowired
    SemesterRepository semesterRepo;

    @Override
    public Semester addOrUpdate(Semester semester) {
        return this.semesterRepo.addOrUpdate(semester);
    }

    @Override
    public Collection<Semester> getSemesters() {
        return this.semesterRepo.getSemesters();
    }

    @Override
    public Collection<ExtraActivity> getActivitesByTermId(int semesterId) {
        return this.semesterRepo.getActivitesByTermId(semesterId);
    }

    @Override
    public Collection<Bulletin> getBulletinsByTermId(int semesterId) {
        return this.semesterRepo.getBulletinsByTermId(semesterId);
    }

    @Override
    public Semester getSemesterById(int id) {
        return this.semesterRepo.getSemesterById(id);
    }
    
}
