/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.ActivityBulletin;
import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.SummaryBulletin;
import com.nhm.repositories.BulletinRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class BulletinRepositoryImpl extends BaseRepositoryImpl implements BulletinRepository {

    @Override
    public Bulletin addOrUpdate(Bulletin bulletin) {
        try {
            super.addOrUpdate(bulletin, Bulletin.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(BulletinRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BulletinRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(BulletinRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Collection<Bulletin> getBulletins() {
        return super.getItems(Bulletin.class);
    }

    @Override
    public void deleteBulletinById(int id) {
        super.removeItemById(id, Bulletin.class);
    }

    @Override
    public Collection<Interaction> getInteractionsByBulletinId(int id) {
        return super.getItemsByObjId(id, Interaction.class, (cb, data) -> {
            return cb.equal(data.get("bulletinId").get("id"), Long.valueOf(id));
        });
    }

    /**
     * get extraActivity of bulletin by bulletinId, need to exec null with wrong type.
     * @param id
     * @return null, obj
     */
    @Override
    public ExtraActivity getActivityByBulletinId(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Bulletin bulletin = s.get(Bulletin.class, Long.valueOf(id));
        
        return bulletin.getExtraActivity();
    }

    /**
     * data return null to exec warning for api with wrong type
     * @param id
     * @return 
     */
    @Override
    public Collection<MissingActivity> getMissingActivityBySummaryBulletinId(int id) {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Bulletin bulletin = s.get(Bulletin.class, Long.valueOf(id));
        
        if (bulletin instanceof SummaryBulletin summary) {
            return summary.getMissingActivityCollection();
        }
        return null;
    }

    @Override
    public Bulletin getBulletinById(int id) {
        return super.getItemById(id, Bulletin.class);
    }
    
}
