/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author GIGABYTE
 * @param <T>
 * @param <TService>
 */
public abstract class BaseDeserializer<T, TService> extends JsonDeserializer<T> {
    
    @Autowired
    protected TService service;
    
    @Override
    public abstract T deserialize(JsonParser jp, DeserializationContext dc) throws IOException;
    
}
