package com.example.pamsbackend.securtiy;

import com.example.pamsbackend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@ComponentScan("com.example.pamsbackend.securtiy")
public class SecurityConfig  {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        System.out.println(auth);
        auth.userDetailsService(userDetailsService);
    }


    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private  CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
    @Autowired
    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customizeAuthenticationSuccessHandler = customizeAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("hej");
        http  .authorizeHttpRequests(configurer ->
                        configurer

                                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/username").hasAnyRole( "USER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/username").hasAnyRole( "USER")

                );
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
            .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
