/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.MissingActivity;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface ExtraActivityRepository {
    ExtraActivity addOrUpdate(ExtraActivity activity);
    Collection<ExtraActivity> getActivities();
    ExtraActivity getActivityById(int activityId);
    Collection<ActivityConfirmedAttendance> getAttendancesByActivityId(int activityId);
    Collection<ActivityRegistry> getResigtriesByActivityId(int activityId);
    Collection<MissingActivity> getMissingsByActivityId(int activityId);
    void deleteActivityById(int id);
}
