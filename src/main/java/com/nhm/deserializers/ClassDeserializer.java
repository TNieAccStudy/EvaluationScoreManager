/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.Classe;
import com.nhm.services.ClassService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class ClassDeserializer extends BaseDeserializer<Classe, ClassService> implements JsonDeserializeMixin<Classe, ClassService> {

    @Override
    public Classe deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public ClassService getService() {
        return this.service;
    }

    @Override
    public Class<Classe> getDeserializedClass() {
        return Classe.class;
    }

    @Override
    public Classe getObjById(ClassService service, Long id) {
        return service.getClassById(id.intValue());
    }
    
}
