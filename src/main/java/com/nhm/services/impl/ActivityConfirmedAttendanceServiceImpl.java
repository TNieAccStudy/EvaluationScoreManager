/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.repositories.ActivityConfirmedAttendanceRepository;
import com.nhm.services.ActivityConfirmedAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class ActivityConfirmedAttendanceServiceImpl implements ActivityConfirmedAttendanceService {
    
    @Autowired
    ActivityConfirmedAttendanceRepository attendanceRepo;

    @Override
    public ActivityConfirmedAttendance addOrUpdate(ActivityConfirmedAttendance activity) {
        return this.attendanceRepo.addOrUpdate(activity);
    }

    @Override
    public ActivityConfirmedAttendance getAttendanceById(int id) {
        return this.attendanceRepo.getAttendanceById(id);
    }
    
}
