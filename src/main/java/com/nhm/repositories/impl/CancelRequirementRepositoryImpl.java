/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.CancelRequirement;
import com.nhm.repositories.CancelRequirementRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GIGABYTE
 */
public class CancelRequirementRepositoryImpl extends BaseRepositoryImpl implements CancelRequirementRepository {

    @Override
    public CancelRequirement addOrUpdate(CancelRequirement cancel) {
        try {
            return super.addOrUpdate(cancel, CancelRequirement.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(CancelRequirementRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CancelRequirementRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CancelRequirementRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteCancelById(int id) {
        super.removeItemById(id, CancelRequirement.class);
    }

    @Override
    public Collection<CancelRequirement> getCancels() {
        return super.getItems(CancelRequirement.class);
    }

    @Override
    public CancelRequirement getCancelById(int id) {
        return super.getItemById(id, CancelRequirement.class);
    }
    
}
