package com.example.pamsbackend.securtiy;

import com.example.pamsbackend.securtiy.config.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(bCryptPasswordEncoder);
//        provider.setUserDetailsService(customUserDetailsService);
//        return provider;
//    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8586", "http://127.0.0.1:8586", "http://127.0.0.1:5500", "localhost:8080", "http://127.0.0.1:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true)
                ;
            }
        };
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer

//                        .requestMatchers(HttpMethod.GET, "/index").permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/hello")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/registration/confirm/**")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/userstatus/**")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/users")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/users/{id}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/token/{id}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/registeruser")).hasRole("NEWUSER")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/users/{id}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/login")).hasRole("USER")
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
