/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.Semester;
import com.nhm.services.SemesterService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class SemesterDeserializer extends BaseDeserializer<Semester, SemesterService> implements JsonDeserializeMixin<Semester, SemesterService> {

    @Override
    public Semester deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public SemesterService getService() {
        return this.service;
    }

    @Override
    public Semester getObjById(SemesterService service, Long id) {
        return service.getSemesterById(id.intValue());
    }

    @Override
    public Class<Semester> getDeserializedClass() {
        return Semester.class;
    }
    
}
