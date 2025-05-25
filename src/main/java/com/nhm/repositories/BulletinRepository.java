/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hibernate.query.Query;

/**
 *
 * @author GIGABYTE
 */
public interface BulletinRepository {
    Bulletin addOrUpdate(Bulletin bulletin);
    <T extends Bulletin> Collection<T> getBulletinsWithParams(Class<T> type, List<BiFunction<CriteriaBuilder, Root<T>, Predicate>> whereParams, Function<Query<T>, Query<T>> supportedQuery);
    void deleteBulletinById(int id);
    <T extends Interaction>Collection<T> getInteractionsByBulletinId(Class<T> type, int id);
    ExtraActivity getActivityByBulletinId(int id);
    Collection<MissingActivity> getMissingActivityBySummaryBulletinId(int id);
    Bulletin getBulletinById(int id);
}
