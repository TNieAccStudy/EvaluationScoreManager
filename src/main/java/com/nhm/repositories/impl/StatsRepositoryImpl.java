/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.dto.StatsDTO;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.repositories.StatsRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 *
 * @author GIGABYTE
 */

@Repository
public class StatsRepositoryImpl extends BaseRepositoryImpl implements StatsRepository {

    @Override
    public Collection<StatsDTO> classStatsWithSemeseter(int semesterId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<StatsDTO> q = cb.createQuery(StatsDTO.class);
        
        Root<ActivityConfirmedAttendance> attendance = q.from(ActivityConfirmedAttendance.class);
        
        q.multiselect(attendance.get("activityRegistryId").get("studentId").get("classId").get("name"),
                cb.sum(attendance.get("activityRegistryId").get("extraActivityId").get("bonusScore")));
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(attendance.get("censorState"), ActivityConfirmedAttendance.CensorState.CONFIRMED.name()));
        predicates.add(cb.equal(attendance.get("activityRegistryId").get("extraActivityId").get("semesterId").get("id"), Long.valueOf(semesterId)));
        q.where(predicates.toArray(new Predicate[0]));
        
        q.groupBy(attendance.get("activityRegistryId").get("studentId").get("classId").get("id"));
        
        TypedQuery<StatsDTO> query = s.createQuery(q);
        
        return query.getResultList();
    }

    @Override
    public Collection<StatsDTO> departmentStatsWithSemeseter(int semesterId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<StatsDTO> q = cb.createQuery(StatsDTO.class);
        
        Root<ActivityConfirmedAttendance> attendance = q.from(ActivityConfirmedAttendance.class);
        
        q.multiselect(attendance.get("activityRegistryId").get("studentId").get("classId").get("departmentId").get("name"),
                cb.sum(attendance.get("activityRegistryId").get("extraActivityId").get("bonusScore")));
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(attendance.get("censorState"), ActivityConfirmedAttendance.CensorState.CONFIRMED.name()));
        predicates.add(cb.equal(attendance.get("activityRegistryId").get("extraActivityId").get("semesterId").get("id"), Long.valueOf(semesterId)));
        q.where(predicates.toArray(new Predicate[0]));
        
        q.groupBy(attendance.get("activityRegistryId").get("studentId").get("classId").get("departmentId").get("id"));
        
        TypedQuery<StatsDTO> query = s.createQuery(q);
        
        return query.getResultList();
    }

    @Override
    public Collection<StatsDTO> achievementStats() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<StatsDTO> q = cb.createQuery(StatsDTO.class);
        
        Root<ActivityConfirmedAttendance> attendance = q.from(ActivityConfirmedAttendance.class);
        
        q.multiselect(attendance.get("activityRegistryId").get("studentId").get("achievement"),
                cb.sum(attendance.get("activityRegistryId").get("extraActivityId").get("bonusScore")));
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(attendance.get("censorState"), ActivityConfirmedAttendance.CensorState.CONFIRMED.name()));
        q.where(predicates.toArray(new Predicate[0]));
        
        q.groupBy(attendance.get("activityRegistryId").get("studentId").get("achievement"));
        
        TypedQuery<StatsDTO> query = s.createQuery(q);
        
        return query.getResultList();
    }

    
}
