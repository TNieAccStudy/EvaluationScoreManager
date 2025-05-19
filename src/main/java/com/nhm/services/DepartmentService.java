/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.services;

import com.nhm.pojo.Classe;
import com.nhm.pojo.Department;
import java.util.Collection;

/**
 *
 * @author GIGABYTE
 */
public interface DepartmentService {
    Department addOrUpdate(Department department);
    Department getDepartmentById(int id);
    Collection<Department> getDepartments();
    Collection<Classe> getClassesByDepartmentId(int id);
}
