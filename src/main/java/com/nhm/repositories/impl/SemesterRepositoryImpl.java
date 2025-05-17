/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Semester;
import com.nhm.repositories.SemesterRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GIGABYTE
 */
public class SemesterRepositoryImpl extends BaseRepositoryImpl implements SemesterRepository {

    @Override
    public Semester addOrUpdate(Semester semester) {
        try {
            return super.addOrUpdate(semester, Semester.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SemesterRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SemesterRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SemesterRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Collection<Semester> getSemesters() {
        return super.getItems(Semester.class);
    }

    @Override
    public Collection<ExtraActivity> getActivitesByTermId(int semesterId) {
        return super.getItemsByObjId(semesterId, ExtraActivity.class, (cb, data) -> {
            return cb.equal(data.get("semesterId").get("id"), Long.valueOf(semesterId));
        });
    }

    @Override
    public Collection<Bulletin> getBulletinsByTermId(int semesterId) {
        return super.getItemsByObjId(semesterId, Bulletin.class, (cb, data) -> {
            return cb.equal(data.get("semesterId").get("id"), Long.valueOf(semesterId));
        });
    }

    @Override
    public Semester getSemesterById(int id) {
        return super.getItemById(id, Semester.class);
    }
    
}
