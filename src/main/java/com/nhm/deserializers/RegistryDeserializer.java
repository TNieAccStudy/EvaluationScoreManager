/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.services.ActivityRegistryService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class RegistryDeserializer extends BaseDeserializer<ActivityRegistry, ActivityRegistryService> implements JsonDeserializeMixin<ActivityRegistry, ActivityRegistryService> {

    @Override
    public ActivityRegistry deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public ActivityRegistryService getService() {
        return this.service;
    }

    @Override
    public ActivityRegistry getObjById(ActivityRegistryService service, Long id) {
        return service.getRegistryById(id.intValue());
    }
    
}
