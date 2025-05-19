/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.nhm.viewconfigs.DisplayView;
import java.io.IOException;

/**
 *
 * @author GIGABYTE
 * @param <T>
 * @param <TService>
 */
public interface InheritanceJsonDeserializeMixin<T, TService> extends JsonDeserializeMixin<T, TService> {

    @Override
    public default T deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        // Không phải object → throw
        if (!node.isObject()) {
            throw new IOException("Expected JSON object for deserialization");
        }

        // Kiểm tra nếu object chỉ có 2 field: "id" và getTypeObjectName()
        boolean hasId = node.has("id") && node.get("id").isIntegralNumber();
        boolean hasType = node.has(getTypeObjectName());
        boolean isPureReference = node.size() == 2 && hasId && hasType;

        if (isPureReference) {
            // Deserialize theo ID từ DB
            Long id = node.get("id").asLong();
            T object = this.getObjById(getService(), id);
            if (object == null) {
                throw new IOException(getDeserializedClass().getSimpleName() + " with id " + id + " not found");
            }
            return object;
        }

        // Nếu là object đầy đủ (dữ liệu dạng embedded), deserialize bình thường
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.readerWithView(DisplayView.Internal.class)
                .forType(getDeserializedClass());
        return reader.readValue(node);
    }

    String getTypeObjectName();
}
