/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.ActivityConfirmedAttendance;

/**
 *
 * @author GIGABYTE
 */
public interface ActivityConfirmedAttendanceRepository {
    ActivityConfirmedAttendance addOrUpdate(ActivityConfirmedAttendance confirmedAttendance);
    ActivityConfirmedAttendance getAttendanceById(int id);
}
