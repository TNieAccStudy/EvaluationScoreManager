/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.Bulletin;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author GIGABYTE
 */
public interface BulletinService {
    Bulletin addOrUpdate(Bulletin bulletin);
    <T extends Bulletin> Collection<T> getBulletins(Map<String, String> params, Class<T> type);
    void deleteBulletinById(int id);
    <T extends Interaction>Collection<T> getInteractionsByBulletinId(int id, Class<T> type);
    ExtraActivity getActivityByBulletinId(int id);
    Collection<MissingActivity> getMissingActivityBySummaryBulletinId(int id);
    Bulletin getBulletinById(int id);
}
