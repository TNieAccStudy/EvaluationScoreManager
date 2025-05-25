/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.MissingActivity;
import com.nhm.services.MissingActivityService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class MissingDeserializer extends BaseDeserializer<MissingActivity, MissingActivityService> implements JsonDeserializeMixin<MissingActivity, MissingActivityService> {

    @Override
    public MissingActivity deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public MissingActivityService getService() {
        return this.service;
    }

    @Override
    public MissingActivity getObjById(MissingActivityService service, Long id) {
        return service.getMissingById(id.intValue());
    }

    @Override
    public Class<MissingActivity> getDeserializedClass() {
        return MissingActivity.class;
    }
    
}
