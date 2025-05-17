/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.Interaction;
import com.nhm.repositories.InteractionRepository;
import com.nhm.services.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class InteractionServiceImpl implements InteractionService {
    
    @Autowired
    InteractionRepository interactionRepo;

    @Override
    public Interaction addOrUpdate(Interaction interaction) {
        return this.interactionRepo.addOrUpdate(interaction);
    }

    @Override
    public void deleteInteractionById(int id) {
        this.interactionRepo.deleteInteractionById(id);
    }

    @Override
    public Interaction getInteractionById(int id) {
        return this.interactionRepo.getInteractionById(id);
    }
    
}
