/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nhm.deserializers.ActivityDeserializer;
import com.nhm.deserializers.BulletinDeserializer;
import com.nhm.deserializers.CancelDeserializer;
import com.nhm.deserializers.ConfirmedAttendanceDeserializer;
import com.nhm.deserializers.InteractionDeserializer;
import com.nhm.deserializers.MissingDeserializer;
import com.nhm.deserializers.RegistryDeserializer;
import com.nhm.deserializers.SemesterDeserializer;
import com.nhm.deserializers.TermDeserializer;
import com.nhm.deserializers.UserDeserializer;
import com.nhm.pojo.ActivityConfirmedAttendance;
import com.nhm.pojo.ActivityRegistry;
import com.nhm.pojo.Bulletin;
import com.nhm.pojo.CancelRequirement;
import com.nhm.pojo.ExtraActivity;
import com.nhm.pojo.Interaction;
import com.nhm.pojo.MissingActivity;
import com.nhm.pojo.Semester;
import com.nhm.pojo.Term;
import com.nhm.pojo.UserInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@ComponentScan({
    "com.nhm.deserializers"
})
public class JacksonConfigs {
    
    @Autowired
    private TermDeserializer termDeserializer;

    @Autowired
    private SemesterDeserializer semesterDeserializer;

    @Autowired
    private UserDeserializer studentAssistantDeserializer;
    
    @Autowired
    private MissingDeserializer missingDeserializer;
    
    @Autowired
    private ActivityDeserializer activityDeserializer;
    
    @Autowired
    private InteractionDeserializer interactionDeserializer;
    
    @Autowired
    private CancelDeserializer cancelDeserializer;
    
    @Autowired
    private BulletinDeserializer bulletinDeserializer;
    
    @Autowired
    private RegistryDeserializer registryDeserializer;
    
    @Autowired
    private ConfirmedAttendanceDeserializer confirmedAttendanceDeserializer;
    
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Term.class, termDeserializer);
        module.addDeserializer(Semester.class, semesterDeserializer);
        module.addDeserializer(UserInfo.class, studentAssistantDeserializer);
        module.addDeserializer(MissingActivity.class, missingDeserializer);
        module.addDeserializer(ExtraActivity.class, activityDeserializer);
        module.addDeserializer(Interaction.class, interactionDeserializer);
        module.addDeserializer(CancelRequirement.class, cancelDeserializer);
        module.addDeserializer(Bulletin.class, bulletinDeserializer);
        module.addDeserializer(ActivityRegistry.class, registryDeserializer);
        module.addDeserializer(ActivityConfirmedAttendance.class, confirmedAttendanceDeserializer);
        

        mapper.registerModule(module);

        return new MappingJackson2HttpMessageConverter(mapper);
    }
    
}