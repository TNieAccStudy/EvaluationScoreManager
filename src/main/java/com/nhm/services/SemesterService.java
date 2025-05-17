/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Semester;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface SemesterService {
    Semester addOrUpdate(Semester semester);
    Collection<Semester> getSemesters();
    Collection<ExtraActivity> getActivitesByTermId(int semesterId);
    Collection<Bulletin> getBulletinsByTermId(int semesterId);
    Semester getSemesterById(int id);
}
