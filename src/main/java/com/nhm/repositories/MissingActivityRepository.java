/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.MissingActivity;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface MissingActivityRepository {
    MissingActivity addOrUpdate(MissingActivity missing);
    MissingActivity getMissingById(int id);
    Collection<MissingActivity> getMissingActivities();
}
