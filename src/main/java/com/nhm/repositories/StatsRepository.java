/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.dto.StatsDTO;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface StatsRepository {
    Collection<StatsDTO> classStatsWithSemeseter(int semesterId);
    Collection<StatsDTO> departmentStatsWithSemeseter(int semesterId);
    Collection<StatsDTO> achievementStats();
}
