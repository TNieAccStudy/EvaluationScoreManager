/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.Bulletin;
import com.nhm.services.BulletinService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class BulletinDeserializer extends BaseDeserializer<Bulletin, BulletinService> implements InheritanceJsonDeserializeMixin<Bulletin, BulletinService> {

    @Override
    public Bulletin deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return InheritanceJsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public String getTypeObjectName() {
        return "bulletinType";
    }

    @Override
    public BulletinService getService() {
        return this.service;
    }

    @Override
    public Bulletin getObjById(BulletinService service, Long id) {
        return service.getBulletinById(id.intValue());
    }
    
}
