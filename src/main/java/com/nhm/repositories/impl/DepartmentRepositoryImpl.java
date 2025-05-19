/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.Classe;
import com.nhm.pojo.Department;
import com.nhm.repositories.DepartmentRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author GIGABYTE
 */
@Repository
@Transactional
public class DepartmentRepositoryImpl extends BaseRepositoryImpl implements DepartmentRepository {

    @Override
    public Department addOrUpdate(Department department) {
        try {
            return super.addOrUpdate(department, Department.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(DepartmentRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DepartmentRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DepartmentRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Department getDepartmentById(int id) {
        return super.getItemById(id, Department.class);
    }

    @Override
    public Collection<Department> getDepartments() {
        return super.getItems(Department.class);
    }

    @Override
    public Collection<Classe> getClassesByDepartmentId(int id) {
        return super.getItemsByObjId(id, Classe.class, (cb, data) -> {
            return cb.equal(data.get("departmentId").get("id"), Long.valueOf(id));
        });
    }
    
}
