/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.nhm.viewconfigs.DisplayView;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author GIGABYTE
 * @param <T>
 * @param <TService>
 */
public interface JsonDeserializeMixin<T, TService> {

    public default T deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        // Nếu là ID dạng số
        if (node.isIntegralNumber()) {
            Long id = node.longValue();
            T t = getObjById(getService(), id);
            if (t == null) {
                throw new RuntimeException(getTypeName() + " not found with ID = " + id);
            }
            return t;
        }
        
        if (!node.isObject()) {
            throw new RuntimeException(getTypeName() + " is not a object and not ID");
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerWithView(DisplayView.Internal.class)
                .forType(getDeserializedClass());
        return reader.readValue(node);
    }

    TService getService();

    Class<T> getDeserializedClass();

    T getObjById(TService service, Long id);

    public default String getTypeName() {
        return getService().getClass().getSimpleName();
    }

}
