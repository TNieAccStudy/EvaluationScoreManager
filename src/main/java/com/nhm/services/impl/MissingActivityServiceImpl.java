/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.MissingActivityRepository;
import com.nhm.services.MissingActivityService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class MissingActivityServiceImpl implements MissingActivityService {
    
    @Autowired
    MissingActivityRepository missingRepo;

    @Override
    public MissingActivity addOrUpdate(MissingActivity missing) {
        return this.missingRepo.addOrUpdate(missing);
    }

    @Override
    public MissingActivity getMissingById(int id) {
        return this.missingRepo.getMissingById(id);
    }

    @Override
    public Collection<MissingActivity> getMissingActivities() {
        return this.missingRepo.getMissingActivities();
    }
    
}
