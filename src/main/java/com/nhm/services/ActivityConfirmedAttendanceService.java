/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.ActivityConfirmedAttendance;

/**
 *
 * @author GIGABYTE
 */
public interface ActivityConfirmedAttendanceService {
    ActivityConfirmedAttendance addOrUpdate(ActivityConfirmedAttendance activity);
    ActivityConfirmedAttendance getAttendanceById(int id);
}
