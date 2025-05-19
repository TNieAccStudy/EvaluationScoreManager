/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.Department;
import com.nhm.services.DepartmentService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class DepartmentDeserializer extends BaseDeserializer<Department, DepartmentService> implements JsonDeserializeMixin<Department, DepartmentService> {

    @Override
    public Department deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public DepartmentService getService() {
        return this.service;
    }

    @Override
    public Class<Department> getDeserializedClass() {
        return Department.class;
    }

    @Override
    public Department getObjById(DepartmentService service, Long id) {
        return service.getDepartmentById(id.intValue());
    }
    
}
