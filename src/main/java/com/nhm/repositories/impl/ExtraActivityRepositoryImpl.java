/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExtraActivity;
import com.nhm.repositories.ExtraActivityRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author GIGABYTE
 */
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
    public Collection<ExtraActivity> getActivitys() {
        return super.getItems(ExtraActivity.class);
    }

    @Override
    public ExtraActivity getActivityById(int activityId) {
        return super.getItemById(activityId, ExtraActivity.class);
    }

    @Override
    public Collection<ActivityConfirmedAttendance> getAttendancesByActivityId(int activityId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<ActivityConfirmedAttendance> q = cb.createQuery(ActivityConfirmedAttendance.class);
        
        Root<ActivityConfirmedAttendance> activityConfirmData = q.from(ActivityConfirmedAttendance.class);
        
        Join<ActivityConfirmedAttendance, ActivityRegistry> registryJoin = activityConfirmData.join("activityRegistryId", JoinType.INNER);
        q.select(activityConfirmData)
            .where(cb.equal(registryJoin.get("extraActivityId"), activityId));
        
        Query<ActivityConfirmedAttendance> query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public Collection<ActivityRegistry> getResigtriesByActivityId(int activityId) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<ActivityRegistry> q = cb.createQuery(ActivityRegistry.class);
        
        Root<ActivityRegistry> registryData = q.from(ActivityRegistry.class);
        q.select(registryData)
            .where(cb.equal(registryData.get("activityRegistryId"), activityId));
        
        Query<ActivityRegistry> query = s.createQuery(q);
        return query.getResultList();
    }
    
}
