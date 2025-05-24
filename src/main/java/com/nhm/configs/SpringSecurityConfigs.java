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
        String ASSISTANT_ROLE = "ASSISTANT";
        String STUDENT_ROLE = "STUDENT";
        String AFFAIRS_ROLE = "AFFAIRS";
        
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(c -> c.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/api/login","/api/users").anonymous()
                        .requestMatchers(HttpMethod.POST, "/api/bulletins", "api/assistants").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/classes", "/api/departments", "/api/semesters", "/api/terms", "api/interactions").permitAll()
                        
                        .requestMatchers("/api/**").authenticated()
                        
                        .requestMatchers(HttpMethod.POST, "/api/missings", "/api/attendances", "api/interactions", "api/registries").hasRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/missings", "/api/attendances", "api/interactions", "api/registries").hasRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/current-student/registries", "/api/current-student/attendances", "/api/current-student/missings").hasRole(STUDENT_ROLE)
                        .requestMatchers(HttpMethod.GET, "/api/secure/profile").hasRole(STUDENT_ROLE)
                        
                        .requestMatchers("/api/attendances", "/api/missings", "/api/students/**").hasAnyRole(ASSISTANT_ROLE, AFFAIRS_ROLE)
                        .requestMatchers("/api/missings", "/api/cancels").hasRole(ASSISTANT_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/api/activities", "/api/bulletins").hasRole(AFFAIRS_ROLE)
                        .requestMatchers("/api/activities/**", "/api/bulletins/**").hasRole(ASSISTANT_ROLE)
                        
                        .requestMatchers("/api/**").permitAll()
                        
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
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}