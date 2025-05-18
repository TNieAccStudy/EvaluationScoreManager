///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.nhm.configs;
//
//import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
//import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// *
// * @author GIGABYTE
// */
//@Configuration
//public class ConfigurationUtilsConfigs {
//    
//    @Bean
//    public PolymorphicTypeValidator basicPolimorphicTypeValidator() {
//        return BasicPolymorphicTypeValidator.builder()
//            .allowIfSubType("com.nhm") // chỉ cho phép deserialize class trong package này
//            .build();
//    }
//}
