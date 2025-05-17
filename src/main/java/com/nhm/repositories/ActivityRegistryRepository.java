/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.ActivityRegistry;

/**
 *
 * @author GIGABYTE
 */
public interface ActivityRegistryRepository {
    ActivityRegistry addOrUpdate(ActivityRegistry registry);
}
