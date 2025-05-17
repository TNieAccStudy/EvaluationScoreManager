/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.Term;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 *
 * @author GIGABYTE
 */
public abstract class BaseRepositoryImpl {
    
    @Autowired
    protected LocalSessionFactoryBean sessionFactory;
    
    protected <T> T addOrUpdate(T obj, Class<T> classType) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Session s = sessionFactory.getObject().getCurrentSession();
        if (obj!=null) {
            Method getMethod = classType.getMethod("getId");
            
            if (getMethod.invoke(obj) == null) {
                s.persist(obj);
            } else
                s.merge(obj);
            
            return obj;
        } else 
            return null;
    }
    
//    protected <T> Collection<T> getItems(Class classType) {
//        Session s = sessionFactory.getObject().getCurrentSession();
//        CriteriaBuilder cb = s.getCriteriaBuilder();
//        CriteriaQuery<T> q = cb.createQuery(classType);
//        
//        Root<T> data = q.from(classType);
//        q.select(data);
//        
//        Query<T> query = s.createQuery(q);
//        
//        return query.getResultList();
//    }
    
    protected <T> Collection<T> getItems(Class<T> classType) {
        Session s = sessionFactory.getObject().getCurrentSession();
        
        Query<T> query = s.createNamedQuery(String.format("%s.findAll",classType.getSimpleName()), classType);
        return query.getResultList();
    }
    
    protected <T> T getItemById(int id, Class<T> classType) {
        Session s = sessionFactory.getObject().getCurrentSession();
        return s.get(classType, id);
    }
}
