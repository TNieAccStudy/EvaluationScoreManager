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
import com.nhm.utils.PaginatorUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hibernate.query.Query;
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
    public Collection<ExtraActivity> getActivities(Map<String, String> params) {
        List<BiFunction<CriteriaBuilder, Root<ExtraActivity>, Predicate>> whereParams = new ArrayList<>();
        
        if (params.containsKey("term")) {
            whereParams.add((cb, root) -> cb.equal(root.get("termId").get("id"), Long.valueOf(params.get("term"))));
        }
        
        if (params.containsKey("semester")) {
            whereParams.add((cb, root) -> cb.equal(root.get("semesterId").get("id"), Long.valueOf(params.get("semester"))));
        }
        
        if (params.containsKey("assistant")) {
            whereParams.add((cb, root) -> cb.equal(root.get("studentAssistantId").get("id"), Long.valueOf(params.get("assistant"))));
        }
        
        if (params.containsKey("kw")) {
            whereParams.add((cb, root) -> cb.like(root.get("title"), params.get("kw")));
        }
        
        Function<Query<ExtraActivity>, Query<ExtraActivity>> supportedQuery = null;
        if (params.containsKey("page")) {
            int pageSize = PaginatorUtils.pageSize;
            int startIndex = Integer.parseInt(params.get("page")) * pageSize;
            supportedQuery = (q) -> {
                q.setFirstResult(startIndex);
                q.setMaxResults(pageSize);
                return q;
            };
        }
        
        return this.activityRepo.getActivities(whereParams, supportedQuery);
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
