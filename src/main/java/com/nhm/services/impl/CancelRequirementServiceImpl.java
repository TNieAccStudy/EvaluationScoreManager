/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.CancelRequirement;
import com.nhm.repositories.CancelRequirementRepository;
import com.nhm.services.CancelRequirementService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class CancelRequirementServiceImpl implements CancelRequirementService {
    
    @Autowired
    private CancelRequirementRepository cancelRepo;

    @Override
    public CancelRequirement addOrUpdate(CancelRequirement cancel) {
        return this.cancelRepo.addOrUpdate(cancel);
    }

    @Override
    public <T extends CancelRequirement> Collection<T> getCancels(Class<T> type) {
        return this.cancelRepo.getCancels(type);
    }

    @Override
    public void deleteCancelById(int id) {
        this.cancelRepo.deleteCancelById(id);
    }

    @Override
    public CancelRequirement getCancelById(int id) {
        return this.cancelRepo.getCancelById(id);
    }
    
}
