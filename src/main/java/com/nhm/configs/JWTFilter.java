/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhm.configs;

import com.nhm.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author GIGABYTE
 */
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        //temp
//        String path = request.getRequestURI();
//        if (path.startsWith("/affairs")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        //close

        String token = JwtUtils.getTokenFromHeader(request);
        if (token != null) {
            try {
                String username = JwtUtils.validateTokenAndGetUsername(token);
                if (username != null) {
                    List<GrantedAuthority> authorities = JwtUtils.extractRoles(token);
                    UsernamePasswordAuthenticationToken auth
                            = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }

}
