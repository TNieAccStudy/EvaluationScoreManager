/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.services.ActivityConfirmedAttendanceService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class ConfirmedAttendanceDeserializer extends BaseDeserializer<ActivityConfirmedAttendance, ActivityConfirmedAttendanceService> implements JsonDeserializeMixin<ActivityConfirmedAttendance, ActivityConfirmedAttendanceService> {

    @Override
    public ActivityConfirmedAttendance deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public ActivityConfirmedAttendanceService getService() {
        return this.service;
    }

    @Override
    public ActivityConfirmedAttendance getObjById(ActivityConfirmedAttendanceService service, Long id) {
        return service.getAttendanceById(id.intValue());
    }
    
}
