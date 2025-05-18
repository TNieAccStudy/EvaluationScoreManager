/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.nhm.pojo.UserInfo;
import com.nhm.services.UserService;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author GIGABYTE
 */
@Component
public class UserDeserializer extends BaseDeserializer<UserInfo, UserService> implements InheritanceJsonDeserializeMixin<UserInfo, UserService> {
    

    @Override
    public UserInfo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return InheritanceJsonDeserializeMixin.super.deserialize(jp, ctxt);
    }

    @Override
    public String getTypeObjectName() {
        return "userType";
    }

    @Override
    public UserService getService() {
        return this.service;
    }

    @Override
    public UserInfo getObjById(UserService service, Long id) {
        return service.getUserById(id.intValue());
    }
    
}
