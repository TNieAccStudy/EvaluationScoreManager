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
import java.util.Collection;
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
    public Collection<Bulletin> getBulletins() {
        return this.bulletinRepo.getBulletins();
    }

    @Override
    public void deleteBulletinById(int id) {
        this.bulletinRepo.deleteBulletinById(id);
    }

    @Override
    public Collection<Interaction> getInteractionsByBulletinId(int id) {
        return this.bulletinRepo.getInteractionsByBulletinId(id);
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
