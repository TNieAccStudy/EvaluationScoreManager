/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.ActivityRegistry;

/**
 *
 * @author GIGABYTE
 */
public interface ActivityRegistryService {
    ActivityRegistry addOrUpdate(ActivityRegistry registry);
    ActivityRegistry getRegistryById(int id);
}
