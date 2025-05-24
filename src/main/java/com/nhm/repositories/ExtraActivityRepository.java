/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.MissingActivity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hibernate.query.Query;

/**
 *
 * @author GIGABYTE
 */
public interface ExtraActivityRepository {
    ExtraActivity addOrUpdate(ExtraActivity activity);
    Collection<ExtraActivity> getActivities(List<BiFunction<CriteriaBuilder, Root<ExtraActivity>, Predicate>> whereParams, Function<Query<ExtraActivity>, Query<ExtraActivity>> supportedQuery);
    ExtraActivity getActivityById(int activityId);
    Collection<ActivityConfirmedAttendance> getAttendancesByActivityId(int activityId);
    Collection<ActivityRegistry> getResigtriesByActivityId(int activityId);
    Collection<MissingActivity> getMissingsByActivityId(int activityId);
    void deleteActivityById(int id);
}
