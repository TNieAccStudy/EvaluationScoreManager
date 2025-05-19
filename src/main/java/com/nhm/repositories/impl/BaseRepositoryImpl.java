/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.BiFunction;
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
        if (obj == null) {
            System.out.println("Object to save is null");
            return null;
        }
        
        Session s = this.sessionFactory.getObject().getCurrentSession();
        try {
            Method getIdMethod = obj.getClass().getMethod("getId");
            Object idValue = getIdMethod.invoke(obj);

            if (idValue == null) {
                System.out.println("Persisting new object");
                s.persist(obj);
            } else {
                System.out.println("Merging existing object with id = " + idValue);
                obj = (T) s.merge(obj);
            }

            s.refresh(obj);

            System.out.println("After flush, obj = " + obj);
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

        Query<T> query = s.createNamedQuery(String.format("%s.findAll", classType.getSimpleName()), classType);
        return query.getResultList();
    }

    protected <T> T getItemById(int id, Class<T> classType) {
        Session s = sessionFactory.getObject().getCurrentSession();
        return s.get(classType, id);
    }

    protected <T> void removeItemById(int id, Class<T> classType) {
        Session s = sessionFactory.getObject().getCurrentSession();

        T obj = s.get(classType, id);
        s.remove(obj);
    }

    protected <T> Collection<T> getItemsByObjId(int objId, Class<T> resultType,
            BiFunction<CriteriaBuilder, Root<T>, Expression<Boolean>>... whereConditions) {
        Session s = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(resultType);

        Root<T> data = q.from(resultType);
        q.select(data);

        for (var w : whereConditions) {
            q.where(w.apply(cb, data));
        }

        Query<T> query = s.createQuery(q);
        return query.getResultList();
    }

    protected <T> Collection<T> getItemsByObjIdWithCustomQuery(int objId, Class<T> resultType,
            BiFunction<CriteriaBuilder, CriteriaQuery<T>, CriteriaQuery<T>> execQuery) {
        Session s = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(resultType);

        q = execQuery.apply(cb, q);

        Query<T> query = s.createQuery(q);
        return query.getResultList();
    }
}
