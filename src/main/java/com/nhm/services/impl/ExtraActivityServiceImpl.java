/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.ExtraActivityRepository;
import com.nhm.services.ExtraActivityService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class ExtraActivityServiceImpl implements ExtraActivityService {
    
    @Autowired
    ExtraActivityRepository activityRepo;

    @Override
    public ExtraActivity addOrUpdate(ExtraActivity activity) {
        return this.activityRepo.addOrUpdate(activity);
    }

    @Override
    public Collection<ExtraActivity> getActivities() {
        return this.activityRepo.getActivities();
    }

    @Override
    public ExtraActivity getActivityById(int activityId) {
        return this.activityRepo.getActivityById(activityId);
    }

    @Override
    public Collection<ActivityConfirmedAttendance> getAttendancesByActivityId(int activityId) {
        return this.activityRepo.getAttendancesByActivityId(activityId);
    }

    @Override
    public Collection<ActivityRegistry> getResigtriesByActivityId(int activityId) {
        return this.activityRepo.getResigtriesByActivityId(activityId);
    }

    @Override
    public Collection<MissingActivity> getMissingsByActivityId(int activityId) {
        return this.activityRepo.getMissingsByActivityId(activityId);
    }

    @Override
    public void deleteActivityById(int id) {
        this.activityRepo.deleteActivityById(id);
    }
    
}
