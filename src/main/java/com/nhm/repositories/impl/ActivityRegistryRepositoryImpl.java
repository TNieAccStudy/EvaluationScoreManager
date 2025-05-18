/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityRegistry;
import com.nhm.repositories.ActivityRegistryRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class ActivityRegistryRepositoryImpl extends BaseRepositoryImpl implements ActivityRegistryRepository {

    @Override
    public ActivityRegistry addOrUpdate(ActivityRegistry registry) {
        try {
            return super.addOrUpdate(registry, ActivityRegistry.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ActivityRegistryRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActivityRegistryRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ActivityRegistryRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ActivityRegistry getRegistryById(int id) {
        return super.getItemById(id, ActivityRegistry.class);
    }

    
}
