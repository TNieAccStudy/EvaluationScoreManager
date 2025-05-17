/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.CancelRequirement;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface CancelRequirementRepository {
    CancelRequirement addOrUpdate(CancelRequirement cancel);
    Collection<CancelRequirement> getCancels();
    void deleteCancelById(int id);
    CancelRequirement getCancelById(int id);
}
