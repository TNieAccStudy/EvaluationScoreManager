/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.ActivityRegistry;
import com.nhm.repositories.ActivityRegistryRepository;
import com.nhm.services.ActivityRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class ActivityRegistryServiceImpl implements ActivityRegistryService {
    
    @Autowired
    ActivityRegistryRepository registryRepo;

    @Override
    public ActivityRegistry addOrUpdate(ActivityRegistry registry) {
        return this.registryRepo.addOrUpdate(registry);
    }

    @Override
    public ActivityRegistry getRegistryById(int id) {
        return this.registryRepo.getRegistryById(id);
    }
    
}
