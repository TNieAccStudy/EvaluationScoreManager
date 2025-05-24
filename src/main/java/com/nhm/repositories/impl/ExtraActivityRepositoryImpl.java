/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.ExtraActivityRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class ExtraActivityRepositoryImpl extends BaseRepositoryImpl implements ExtraActivityRepository {

    @Override
    public ExtraActivity addOrUpdate(ExtraActivity activity) {
        try {
            return super.addOrUpdate(activity, ExtraActivity.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ExtraActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ExtraActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ExtraActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Collection<ExtraActivity> getActivities() {
        return super.getItems(ExtraActivity.class);
    }

    @Override
    public ExtraActivity getActivityById(int activityId) {
        return super.getItemById(activityId, ExtraActivity.class);
    }

    @Override
    public Collection<ActivityConfirmedAttendance> getAttendancesByActivityId(int activityId) {
        return super.getItemsByObjId(activityId, ActivityConfirmedAttendance.class, (cb, data) -> {
            return cb.equal(data.get("extraActivityId").get("id"), Long.valueOf(activityId));
        });
    }

    @Override
    public Collection<ActivityRegistry> getResigtriesByActivityId(int activityId) {
        Collection<ActivityRegistry> registries = super.getItemsByObjId(activityId, ActivityRegistry.class, (cb, data) -> {
            return cb.equal(data.get("extraActivityId").get("id"), Long.valueOf(activityId));
        });
        System.out.println("data of registries");
        registries.stream().forEach(r -> System.out.println("data of registry : " + r));
        
        return registries;
    }

    @Override
    public void deleteActivityById(int id) {
        super.removeItemById(id, ExtraActivity.class);
    }

    @Override
    public Collection<MissingActivity> getMissingsByActivityId(int activityId) {
        return super.getItemsByObjId(activityId, MissingActivity.class, (cb, data) -> {
            return cb.equal(data.get("extraActivityId").get("id"), Long.valueOf(activityId));
        });
    }
    
}
