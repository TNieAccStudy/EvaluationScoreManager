/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.Student;
import com.nhm.pojo.UserInfo;
import com.nhm.repositories.UserRepository;
import java.util.Collection;
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
        s.persist(u);
        
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
}
