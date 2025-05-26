/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.ExecuteStatus;
import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.MissingActivityRepository;
import com.nhm.utils.CloudinaryUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class MissingActivityRepositoryImpl extends BaseRepositoryImpl implements MissingActivityRepository {
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public MissingActivity addOrUpdate(MissingActivity missing) {
        try {
            String censorStateChange = ActivityConfirmedAttendance.CensorState
                                .responseCensorStateFormExecStatus(ExecuteStatus.valueOf(missing.getExecutedStatus())).name();
            
            if (missing.getActivityConfirmedAttendanceId() != null) {
                ActivityConfirmedAttendance attendance = missing.getActivityConfirmedAttendanceId();
                attendance.setCensorState(censorStateChange);
                Session s = sessionFactory.getObject().getCurrentSession();
                
                s.merge(attendance);
                s.flush();
            } else {
                Session s = this.sessionFactory.getObject().getCurrentSession();
                CriteriaBuilder cb = s.getCriteriaBuilder();
                
                CriteriaQuery<ActivityRegistry> q = cb.createQuery(ActivityRegistry.class);
                Root<ActivityRegistry> registryData = q.from(ActivityRegistry.class);
                q.select(registryData);
                q.where(cb.and(
                        cb.equal(registryData.get("extraActivityId").get("id"), missing.getExtraActivityId().getId()),
                        cb.equal(registryData.get("studentId").get("id"), missing.getStudentId().getId())
                ));
                
                Query<ActivityRegistry> qRegistry = s.createQuery(q);
                ActivityRegistry registry = qRegistry.getSingleResult();
                
                ActivityConfirmedAttendance attendance;
                if (registry.getActivityConfirmedAttendance() != null) {
                    attendance = registry.getActivityConfirmedAttendance();
                    String publicId = CloudinaryUtils.extractPublicId(missing.getProofPicture());
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                    
                } else {
                    attendance = new ActivityConfirmedAttendance();
                    attendance.setActivityRegistryId(registry);
                }
                
                attendance.setProofPicture(missing.getProofPicture());
                attendance.setCensorState(censorStateChange);
                
                if (attendance.getId() == null) {
                    s.persist(attendance);
                } else {
                    s.merge(attendance);
                    s.flush();
                }
                
                s.refresh(attendance);
                
                missing.setActivityConfirmedAttendanceId(attendance);
                
            }
            
            return super.addOrUpdate(missing, MissingActivity.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MissingActivityRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public MissingActivity getMissingById(int id) {
        return super.getItemById(id, MissingActivity.class);
    }
    
}
