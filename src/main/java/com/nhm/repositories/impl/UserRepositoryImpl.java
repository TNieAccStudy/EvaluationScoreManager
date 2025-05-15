/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.UserInfo;
import com.nhm.repositories.UserRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserInfo getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByUsername", UserInfo.class);
        q.setParameter("username", username);

        return (UserInfo) q.getSingleResult();

    }

    @Override
    public UserInfo addUser(UserInfo u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
        
        return u;
    }

    @Override
    public boolean authenticate(String username, String password) {
        UserInfo u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }
}
