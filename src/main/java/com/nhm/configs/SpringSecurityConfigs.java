/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.configs;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 *
 * @author admin
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.nhm.controllers",
    "com.nhm.repositories",
    "com.nhm.services"
})
public class SpringSecurityConfigs {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
//            Exception {
//        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(c -> c.disable())
//                   .authorizeHttpRequests(requests ->
//                 requests.requestMatchers("/", "/home").authenticated()
//                        .requestMatchers("/api/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/products").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET,
//                                "/products/**").hasAnyRole("USER", "ADMIN")
//                        .anyRequest().authenticated())
//                .formLogin(form -> form.loginPage("/login")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/", true)
//                .failureUrl("/login?error=true").permitAll())
//                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());
//        return http.build();
//    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        String[] STUDENT_ROLE = new String[]{"STUDENT", "STUDENTASSISTANT", "AFFAIRS"};
        String[] ASSISTANT_ROLE = new String[]{"STUDENTASSISTANT", "AFFAIRS"};
        String AFFAIRS_ROLE = "AFFAIRS";
        
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                        
                        .requestMatchers(HttpMethod.POST, "/api/missings", "/api/attendances", "/api/interactions", "/api/registries").hasAnyRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/missings", "/api/attendances", "/api/interactions", "/api/registries").hasAnyRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/students/current-student/**").hasAnyRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/secure/profile").authenticated()
                        
                        .requestMatchers(HttpMethod.DELETE, "/api/activities", "/api/bulletins").hasRole(AFFAIRS_ROLE)
                        .requestMatchers("/api/missings", "/api/cancels").hasAnyRole(ASSISTANT_ROLE)
                        .requestMatchers("/api/activities/**", "/api/bulletins/**").hasAnyRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.POST, "/api/attendances", "/api/missings", "/api/students/**").hasAnyRole(ASSISTANT_ROLE)
                        
        
                        
                        .requestMatchers(HttpMethod.POST, "/api/login","/api/users").anonymous()
                        .requestMatchers(HttpMethod.GET, "/api/classes", "/api/departments", "/api/semesters", "/api/terms", "api/interactions").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/bulletins", "/api/assistants").permitAll()
                        
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/affairs/**").permitAll()
                        
                )
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of("http://localhost:3000/")); 
        config.setAllowedMethods(List.of("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}