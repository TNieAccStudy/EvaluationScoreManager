/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
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
                s.flush();
            }

            s.refresh(obj); 

            System.out.println("After flush, obj = " + obj);
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    
    protected <T> Collection<T> getItems(Class<T> classType) {
        Session s = sessionFactory.getObject().getCurrentSession();

        Query<T> query = s.createNamedQuery(String.format("%s.findAll", classType.getSimpleName()), classType);
        return query.getResultList();
    }
    
    protected <T> Collection<T> getItems(Class<T> resultType,
            List<BiFunction<CriteriaBuilder, Root<T>, Predicate>> whereConditions,
            Function<Query<T>, Query<T>> supportedQuery) {
        Session s = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(resultType);

        Root<T> data = q.from(resultType);
        q.select(data);
        
        List<Predicate> predicates = new ArrayList<>();
        whereConditions.forEach(w -> predicates.add(w.apply(cb, data)));
        q.where(predicates.toArray(new Predicate[0]));

        Query<T> query = s.createQuery(q);
        if (supportedQuery != null)
            query = supportedQuery.apply(query);
        
        return query.getResultList();
    }
    
    protected <T> Collection<T> getItems(Class<T> resultType,
            List<BiFunction<CriteriaBuilder, Root<T>, Predicate>> whereConditions) {
        return this.getItems(resultType, whereConditions, (q)->q);
    }

    protected <T> Collection<T> getItems(Class<T> resultType,
            BiFunction<CriteriaBuilder, Root<T>, Predicate>... whereConditions) {
        return this.getItems(resultType, 
                Arrays.stream(whereConditions).collect(Collectors.toList())
        );
    }

    protected <T> Collection<T> getItemsWithCustomQuery(Class<T> resultType,
            BiFunction<CriteriaBuilder, CriteriaQuery<T>, CriteriaQuery<T>> execQuery) {
        Session s = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(resultType);

        q = execQuery.apply(cb, q);

        Query<T> query = s.createQuery(q);
        
        return query.getResultList();
    }
    
    //example if call where with cutomQuery
    //call customquery -> then exec like this for repo:
    //Root general to get all type.
//    @Override
//    public Collection<Bulletin> getBulletinsWithParams(int id, Map<Root<?>, BiFunction<CriteriaBuilder, Root, Expression<Boolean>>> whereParams) {
//        BiFunction<CriteriaBuilder, Root<Bulletin>, Expression<Boolean>>[] whereConditions = whereParams.entrySet().stream()
//                    .map(e -> e.getValue())
//                    .toArray(BiFunction<CriteriaBuilder, Root, Expression<Boolean>>[]::new);
//        return super.getItemsByObjId(id, Bulletin.class, 
//                whereConditions
//        );
//    }
    // and execute it in execQuery.
    
    
}
