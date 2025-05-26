/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.ActivityConfirmedAttendance;
import java.io.IOException;
import java.util.Collection;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author GIGABYTE
 */
public interface ActivityConfirmedAttendanceService {
    ActivityConfirmedAttendance addOrUpdate(ActivityConfirmedAttendance activity, MultipartFile proofPicture) throws IOException, Exception;
    ActivityConfirmedAttendance getAttendanceById(int id);
    Collection<ActivityConfirmedAttendance> getAttendances();
}
