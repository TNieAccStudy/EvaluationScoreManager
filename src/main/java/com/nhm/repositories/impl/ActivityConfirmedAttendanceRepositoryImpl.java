/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.repositories.ActivityConfirmedAttendanceRepository;
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
public class ActivityConfirmedAttendanceRepositoryImpl extends BaseRepositoryImpl implements ActivityConfirmedAttendanceRepository {

    @Override
    public ActivityConfirmedAttendance addOrUpdate(ActivityConfirmedAttendance confirmedAttendance) {
        try {
            return super.addOrUpdate(confirmedAttendance, ActivityConfirmedAttendance.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ActivityConfirmedAttendanceRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActivityConfirmedAttendanceRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ActivityConfirmedAttendanceRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ActivityConfirmedAttendance getAttendanceById(int id) {
        return super.getItemById(id, ActivityConfirmedAttendance.class);
    }
    
}
