/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.Interaction;
import com.nhm.repositories.InteractionRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GIGABYTE
 */
public class InteractionRepositoryImpl extends BaseRepositoryImpl implements InteractionRepository {

    @Override
    public Interaction addOrUpdate(Interaction interaction) {
        try {
            return super.addOrUpdate(interaction, Interaction.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InteractionRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InteractionRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(InteractionRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteInteractionById(int id) {
        super.removeItemById(id, Interaction.class);
    }

    @Override
    public Interaction getInteractionById(int id) {
        return super.getItemById(id, Interaction.class);
    }
    
}
