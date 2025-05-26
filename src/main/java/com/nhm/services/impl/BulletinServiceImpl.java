/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import com.nhm.repositories.BulletinRepository;
import com.nhm.services.BulletinService;
import com.nhm.utils.PaginatorUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class BulletinServiceImpl implements BulletinService {
    
    @Autowired
    BulletinRepository bulletinRepo;

    @Override
    public Bulletin addOrUpdate(Bulletin bulletin) {
        return this.bulletinRepo.addOrUpdate(bulletin);
    }

    @Override
    public <T extends Bulletin> Collection<T> getBulletins(Map<String, String> params, Class<T> type) {
        List<BiFunction<CriteriaBuilder, Root<T>, Predicate>> whereParams = new ArrayList<>();
        
//        if (params.containsKey("term")) {
//            whereParams.add((cb, root) -> cb.equal(root.get("termId").get("id"), Long.valueOf(params.get("term"))));
//        }
        
        if (params.containsKey("semester")) {
            whereParams.add((cb, root) -> cb.equal(root.get("semesterId").get("id"), Long.valueOf(params.get("semester"))));
        }
        
        if (params.containsKey("assistant")) {
            whereParams.add((cb, root) -> cb.equal(root.get("studentAssistantId").get("id"), Long.valueOf(params.get("assistant"))));
        }
        
        if (params.containsKey("kw")) {
            whereParams.add((cb, root) -> cb.like(root.get("title"), params.get("kw")));
        }
        
        int pageSize = PaginatorUtils.pageSize;
        int startIndex = 0;
        if (params.containsKey("page")) {
            startIndex = (Integer.parseInt(params.get("page"))-1) * pageSize;
        }
        Function<Query<T>, Query<T>> supportedQuery = PaginatorUtils.pageQueryDefault(startIndex, pageSize, type);
        
        return this.bulletinRepo.getBulletinsWithParams(type, whereParams, supportedQuery);
    }
    //need to ask for S in SOLID about problem: is cur code affect to (not carry on other?)

    @Override
    public void deleteBulletinById(int id) {
        this.bulletinRepo.deleteBulletinById(id);
    }

    @Override
    public <T extends Interaction>Collection<T> getInteractionsByBulletinId(int id, Class<T> type) {
        return this.bulletinRepo.getInteractionsByBulletinId(type, id);
    }

    @Override
    public ExtraActivity getActivityByBulletinId(int id) {
        return this.bulletinRepo.getActivityByBulletinId(id);
    }

    @Override
    public Collection<MissingActivity> getMissingActivityBySummaryBulletinId(int id) {
        return this.bulletinRepo.getMissingActivityBySummaryBulletinId(id);
    }

    @Override
    public Bulletin getBulletinById(int id) {
        return this.bulletinRepo.getBulletinById(id);
    }
    
}
