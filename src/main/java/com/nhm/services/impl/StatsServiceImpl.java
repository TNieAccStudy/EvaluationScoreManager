/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.services.impl;

import com.nhm.dto.StatsDTO;
import com.nhm.repositories.StatsRepository;
import com.nhm.services.StatsService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GIGABYTE
 */
@Service
public class StatsServiceImpl implements StatsService {
    
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public Collection<StatsDTO> classStatsWithSemeseter(int semesterId) {
        return this.statsRepo.classStatsWithSemeseter(semesterId);
    }

    @Override
    public Collection<StatsDTO> departmentStatsWithSemeseter(int semesterId) {
        return this.statsRepo.departmentStatsWithSemeseter(semesterId);
    }

    @Override
    public Collection<StatsDTO> achievementStats() {
        return this.statsRepo.achievementStats();
    }
    
}
