/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.dto.CSVAttendancesData;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.Student;
import com.nhm.pojo.UserInfo;
import com.nhm.repositories.ActivityConfirmedAttendanceRepository;
import com.nhm.repositories.UserRepository;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class UserRepositoryImpl extends BaseRepositoryImpl implements UserRepository {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private ActivityConfirmedAttendanceRepository attendaceRepo;

    @Override
    public UserInfo getUserByUsername(String username) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query<UserInfo> q = s.createNamedQuery("UserInfo.findByUsername", UserInfo.class);
        q.setParameter("username", username);

        return q.getSingleResult();
    }

    @Override
    public UserInfo addUser(UserInfo u) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        if(u.getId()==null){
            s.persist(u);
        }
        else{
            s.merge(u);
        }
        return u;
    }

    @Override
    public boolean authenticate(String username, String password) {
        UserInfo u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public UserInfo getUserById(int id) {
        return super.getItemById(id, UserInfo.class);
    }
    
    /**
     * Data return null to exec api with wrong type
     * @param userId
     * @return 
     */
    protected <T> Collection<T> getItemsOfUserByUserId(int userId, Class<T> itemType, Function<Student, Collection<T>> itemsExecPick) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        UserInfo user = s.get(UserInfo.class, Long.valueOf(userId));
        
        if (user instanceof Student student) {
            return itemsExecPick.apply(student);
        } 
        return null;
    }

    /**
     * Data return null to exec api with wrong type
     * @param userId
     * @return 
     */
    @Override
    public Collection<ActivityRegistry> getRegistriesByUserId(int userId) {
        return this.getItemsOfUserByUserId(userId, ActivityRegistry.class, t -> {
            Hibernate.initialize(t.getActivityRegistryCollection());
            return t.getActivityRegistryCollection();
        });
    }

    /**
     * Data return null to exec api with wrong type
     * @param userId
     * @return 
     */
    @Override
    public Collection<ActivityConfirmedAttendance> getAttendsByUserId(int userId) {
        return this.getItemsOfUserByUserId(userId, ActivityConfirmedAttendance.class, 
                t -> {
                    Hibernate.initialize(t.getActivityRegistryCollection());
                    return t.getActivityRegistryCollection().stream()
                    .map(ActivityRegistry::getActivityConfirmedAttendance)
                    .collect(Collectors.toList());
                });
    }

    /**
     * Data return null to exec api with wrong type
     * @param userId
     * @return 
     */
    @Override
    public Collection<MissingActivity> getMissingsByUserId(int userId) {
        return this.getItemsOfUserByUserId(userId, MissingActivity.class, t -> {
            Hibernate.initialize(t.getMissingActivityCollection());
            return t.getMissingActivityCollection();
        });
    }

    @Override
    public Collection<UserInfo> getUsers() {
        return super.getItems(UserInfo.class);
    }

    @Override
    public <T extends UserInfo> Collection<T> getUsers(Class<T> type) {
        return super.getItems(type);
    }

    @Override
    public UserInfo updateUser(UserInfo u) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        s.merge(u);
        
        s.flush();
        
        return u;
    }
    
    private int getEvaluationScoreByUserIdWithRegistryPredicate(int studentId, 
            BiFunction<CriteriaBuilder, Root<ActivityConfirmedAttendance>, Predicate> execRegistryPredicate) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Integer> q = cb.createQuery(Integer.class);
        
        Root<ActivityConfirmedAttendance> attendance = q.from(ActivityConfirmedAttendance.class);
        
        q.select(cb.sum(attendance.get("activityRegistryId").get("extraActivityId").get("bonusScore")));
        
        List<Predicate> predicates = new ArrayList<>();
        
        predicates.add(cb.equal(attendance.get("censorState"), ActivityConfirmedAttendance.CensorState.CONFIRMED.name()));
        
        if (execRegistryPredicate != null) {
            predicates.add(execRegistryPredicate.apply(cb, attendance));
        }
        
        q.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<Integer> totalScoreQuery = s.createQuery(q);
        
        return totalScoreQuery.getSingleResult();
    }

    @Override
    public int getEvaluationScoreByUserIdOfSemesterId(int studentId, int semesterId) {
        return getEvaluationScoreByUserIdWithRegistryPredicate(studentId, (cb, attendance) -> cb.and(
                cb.equal(attendance.get("activityRegistryId").get("studentId").get("id"), Long.valueOf(studentId)),
                cb.equal(attendance.get("activityRegistryId").get("extraActivityId").get("semesterId").get("id"), Long.valueOf(semesterId))
        ));
    }

    @Override
    public int getTotalEvaluationScoreByUserId(int studentId) {
        return getEvaluationScoreByUserIdWithRegistryPredicate(studentId, 
                (cb, attendance) -> cb.equal(attendance.get("activityRegistryId").get("studentId").get("id"), Long.valueOf(studentId))
        );
    }

    @Override
    public Student getStudentByMssv(String mssv) {
        return this.sessionFactory.getObject().getCurrentSession().createNamedQuery("Student.findByMssv", Student.class).getSingleResult();
    }

    @Override
    public Collection<ActivityConfirmedAttendance> loadAttendanceFromCSVAttendanceData(CSVAttendancesData csvAttendanceData) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<ActivityRegistry> q = cb.createQuery(ActivityRegistry.class);
        Root<ActivityRegistry> registryData = q.from(ActivityRegistry.class);
        
        Predicate studentsPredicate = registryData.get("student").get("mssv").in(csvAttendanceData.getAttendedStudents());
        Predicate activityPredicate = cb.equal(registryData.get("extraActivityId").get("id"), csvAttendanceData.getExtraActivityId().getId());
        
        q.select(registryData).where(studentsPredicate, activityPredicate);
        
        Query<ActivityRegistry> qRegistries = s.createQuery(q);
        
        List<ActivityRegistry> registries = qRegistries.getResultList();
        
        List<ActivityConfirmedAttendance> attendances = registries.stream().map(r -> {
            ActivityConfirmedAttendance attendance = new ActivityConfirmedAttendance();
            attendance.setActivityRegistryId(r);
            attendance.setCensorState(ActivityConfirmedAttendance.CensorState.CONFIRMED.name());
            attendance.setProofPicture(csvAttendanceData.getProofPictureGeneral());
            
            return attendance;
        }).collect(Collectors.toList());
        
        return this.attendaceRepo.responseAndAddListAttendance(attendances);
        
    }
}
