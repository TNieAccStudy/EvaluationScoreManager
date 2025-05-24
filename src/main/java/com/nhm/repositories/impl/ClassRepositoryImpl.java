/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.repositories.impl;

import com.nhm.pojo.Classe;
import com.nhm.pojo.Student;
import com.nhm.repositories.ClassRepository;
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
public class ClassRepositoryImpl extends BaseRepositoryImpl implements ClassRepository {

    @Override
    public Classe addOrUpdate(Classe classe) {
        try {
            return super.addOrUpdate(classe, Classe.class);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ClassRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClassRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ClassRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Classe getClassById(int id) {
        return super.getItemById(id, Classe.class);
    }

    @Override
    public Collection<Classe> getClasses() {
        return super.getItems(Classe.class);
    }

    @Override
    public Collection<Student> getStudentsByClassId(int id) {
        return super.getItems(Student.class, (cb, data) -> cb.equal(data.get("classId").get("id"), Long.valueOf(id)));
    }
    
}
