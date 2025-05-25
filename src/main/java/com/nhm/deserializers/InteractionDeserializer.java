/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.Interaction;
import com.nhm.services.InteractionService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class InteractionDeserializer extends BaseDeserializer<Interaction, InteractionService> implements InheritanceJsonDeserializeMixin<Interaction, InteractionService> {

    @Override
    public Interaction deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return InheritanceJsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public String getTypeObjectName() {
        return "interactionType";
    }

    @Override
    public InteractionService getService() {
        return this.service;
    }

    @Override
    public Interaction getObjById(InteractionService service, Long id) {
        return service.getInteractionById(id.intValue());
    }

    @Override
    public Class<Interaction> getDeserializedClass() {
        return Interaction.class;
    }
    
}
