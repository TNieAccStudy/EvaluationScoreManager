/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.configs;

import com.nhm.formatters.SemesterFormatter;
import com.nhm.formatters.AsisstantFormatter;
import com.nhm.formatters.TermFormatter;
import com.nhm.formatters.UserFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author admin
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.nhm.controllers",
    "com.nhm.repositories",
    "com.nhm.services"
})
public class WebAppContextConfigs implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new ExtraActivityFormatter());
//        registry.addFormatter(new SemesterFormatter());
//        registry.addFormatter(new TermFormatter());
//        registry.addFormatter(new UserFormatter());
//        registry.addFormatter(new AsisstantFormatter());
    }
    
    @Autowired
    private MappingJackson2HttpMessageConverter customConverter;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, customConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        
        registry.addResourceHandler("/css/**")
            .addResourceLocations("classpath:/static/css/");
        
        registry.addResourceHandler("/affairs/**")
            .addResourceLocations("classpath:/static/affairs/");
    }
    
}
