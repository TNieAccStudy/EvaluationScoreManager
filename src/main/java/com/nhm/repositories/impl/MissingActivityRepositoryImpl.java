/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.MissingActivityRepository;
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
public class MissingActivityRepositoryImpl extends BaseRepositoryImpl implements MissingActivityRepository {

    @Override
    public MissingActivity addOrUpdate(MissingActivity missing) {
        try {
            super.addOrUpdate(missing, MissingActivity.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public MissingActivity getMissingById(int id) {
        return super.getItemById(id, MissingActivity.class);
    }
    
}
