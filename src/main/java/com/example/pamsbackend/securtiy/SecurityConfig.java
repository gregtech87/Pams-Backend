//package com.example.pamsbackend.securtiy;
//import com.example.pamsbackend.service.SecUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    SecUserDetailsService userDetailsService ;
//
////    @Autowired
////    public void configAuthBuilder(AuthenticationManagerBuilder builder) throws Exception {
////        builder.userDetailsService(userDetailsService);
////    }
//@Bean
//public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//    return authConfig.getAuthenticationManager();
//}
//    Authentication authentication =
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//// userDetails.getUsername()
//// userDetails.getPassword()
//// userDetails.getAuthorities()
//
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://localhost:8585", "http://127.0.0.1:8585", "http://127.0.0.1:5500")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("*")
//                        .exposedHeaders("Authorization")
//                        .allowCredentials(true)
//                ;
//            }
//        };
//    }
//    @Bean
//    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
//        return new MvcRequestMatcher.Builder(introspector);
//    }
//    @Bean
//    public SecurityFilterChain defaultFilterChain(HttpSecurity httpSecurity, MvcRequestMatcher.Builder mvc) throws Exception {
//        httpSecurity
//                .csrf(csrf-> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//        ;
//        httpSecurity.authorizeHttpRequests(configurer ->
//                configurer
//
//                        .requestMatchers(HttpMethod.GET, "/api/v1/username").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/users")).hasAnyRole("USER", "ADMIN")
//        );
//        return httpSecurity.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
