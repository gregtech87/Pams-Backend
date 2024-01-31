/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pamsbackend.securtiy;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
//public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomizeAuthenticationSuccessHandler{

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response, Authentication authentication)
//            throws IOException, ServletException {
//        //set our response to OK status
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        for (GrantedAuthority auth : authentication.getAuthorities()) {
//            if ("ADMIN".equals(auth.getAuthority())) {
//                response.sendRedirect("/dashboard");
//            }
//        }
//    }
        @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

}
