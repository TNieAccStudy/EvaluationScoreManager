/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.repositories;

import com.nhm.pojo.Classe;
import com.nhm.pojo.Student;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface ClassRepository {
    Classe addOrUpdate(Classe classe);
    Classe getClassById(int id);
    Collection<Classe> getClasses();
    Collection<Student> getStudentsByClassId(int id);
}
