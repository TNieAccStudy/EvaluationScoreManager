/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.MissingActivity;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author GIGABYTE
 */
public interface ExtraActivityService {
    ExtraActivity addOrUpdate(ExtraActivity activity);
    Collection<ExtraActivity> getActivities(Map<String, String> params);
    ExtraActivity getActivityById(int activityId);
    Collection<ActivityConfirmedAttendance> getAttendancesByActivityId(int activityId);
    Collection<ActivityRegistry> getResigtriesByActivityId(int activityId);
    Collection<MissingActivity> getMissingsByActivityId(int activityId);
    void deleteActivityById(int id);
}
