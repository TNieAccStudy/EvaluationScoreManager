/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface BulletinRepository {
    Bulletin addOrUpdate(Bulletin bulletin);
    Collection<Bulletin> getBulletins();
    void deleteBulletinById(int id);
    Collection<Interaction> getInteractionsByBulletinId(int id);
    ExtraActivity getActivityByBulletinId(int id);
    Collection<MissingActivity> getMissingActivityBySummaryBulletinId(int id);
    Bulletin getBulletinById(int id);
}
