/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.Term;
import com.nhm.services.TermService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class TermDeserializer extends BaseDeserializer<Term, TermService> implements JsonDeserializeMixin<Term, TermService> {

    @Override
    public Term deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        return JsonDeserializeMixin.super.deserialize(jp, dc);
    }

    @Override
    public TermService getService() {
        return this.service;
    }

    @Override
    public Term getObjById(TermService service, Long id) {
        return service.getTermById(id.intValue());
    }

    @Override
    public Class<Term> getDeserializedClass() {
        return Term.class;
    }

}
