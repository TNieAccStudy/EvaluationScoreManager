/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author GIGABYTE
 */
@Configuration
public class ObjectMapperUtils {
    
    @Autowired
    private PolymorphicTypeValidator polymorphicTypeValidator;
    
    @Bean
    public ObjectMapperUtils getObjctMapperUtils() {
        return new ObjectMapperUtils();
    }
    
    public ObjectMapper createObjectMapper(String propertyName) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        objectMapper.activateDefaultTypingAsProperty(
                polymorphicTypeValidator, 
                ObjectMapper.DefaultTyping.NON_FINAL, 
                propertyName);
        
        return objectMapper;
    }
}
