/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
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

        // Lấy id
        JsonNode idNode = node.get("id");
        if (idNode == null || !idNode.isNumber()) {
            throw new IOException("Missing or invalid id for Object inheritance");
        }
        Long id = idNode.asLong();

        // Lấy userType để kiểm tra (nếu cần)
        JsonNode objTypeNode = node.get(this.getTypeObjectName());
        if (objTypeNode == null) {
            throw new IOException("Invalid " + this.getTypeObjectName() + " for Object inheritance");
        }

        // Gọi service lấy entity từ DB
        T object = this.getObjById(getService(), id);
        if (object == null) {
            throw new IOException("UserInfo with id " + id + " not found");
        }
        return object;
    }

    String getTypeObjectName();
}
