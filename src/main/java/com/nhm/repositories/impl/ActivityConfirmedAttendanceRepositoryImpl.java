/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.Student;
import com.nhm.repositories.ActivityConfirmedAttendanceRepository;
import com.nhm.repositories.SemesterRepository;
import com.nhm.repositories.UserRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class ActivityConfirmedAttendanceRepositoryImpl extends BaseRepositoryImpl implements ActivityConfirmedAttendanceRepository {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private SemesterRepository semesterRepo;

    @Override
    public ActivityConfirmedAttendance addOrUpdate(ActivityConfirmedAttendance confirmedAttendance) {
        try {
            if (confirmedAttendance.getCensorState().equals(ActivityConfirmedAttendance.CensorState.CONFIRMED.name())) {
                Student student = confirmedAttendance.getActivityRegistryId().getStudentId();
                int totalScore = userRepo.getTotalEvaluationScoreByUserId(student.getId().intValue());
                int numberSemester = semesterRepo.getSemesters().size();
                student.setAchievement(Student.Achievement.getAchievementByScore((int)(totalScore/numberSemester)).name());
                
                userRepo.addUser(student);
            }
            
            return super.addOrUpdate(confirmedAttendance, ActivityConfirmedAttendance.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ActivityConfirmedAttendanceRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ActivityConfirmedAttendanceRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ActivityConfirmedAttendanceRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ActivityConfirmedAttendance getAttendanceById(int id) {
        return super.getItemById(id, ActivityConfirmedAttendance.class);
    }

    @Override
    public List<ActivityConfirmedAttendance> responseAndAddListAttendance(List<ActivityConfirmedAttendance> activityConfirmedAttendances) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        
        activityConfirmedAttendances.stream().forEach(a -> s.persist(a));
        
        return activityConfirmedAttendances;
    }
    
}
