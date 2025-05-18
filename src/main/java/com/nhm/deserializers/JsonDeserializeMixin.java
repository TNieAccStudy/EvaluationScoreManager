/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

/**
 *
 * @author GIGABYTE
 * @param <T>
 * @param <TService>
 */
public interface JsonDeserializeMixin<T, TService> {
    public default T deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.isIntegralNumber()) {
            Long id = node.longValue();
            T t = getObjById(getService(), id);
            if (t == null) {
                throw new RuntimeException("Semester not found with ID = " + id);
            }
            return t;
        }

        throw new JsonMappingException(jp, "Expected Semester ID as integer");
    }
    
    TService getService();
    
    T getObjById(TService service, Long id);
}
