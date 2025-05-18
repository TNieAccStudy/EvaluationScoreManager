/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.ExtraActivity;
import com.nhm.services.ExtraActivityService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class ActivityDeserializer extends BaseDeserializer<ExtraActivity, ExtraActivityService> implements JsonDeserializeMixin<ExtraActivity, ExtraActivityService> {

    @Override
    public ExtraActivity deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public ExtraActivityService getService() {
        return this.service;
    }

    @Override
    public ExtraActivity getObjById(ExtraActivityService service, Long id) {
        return service.getActivityById(id.intValue());
    }
    
}
