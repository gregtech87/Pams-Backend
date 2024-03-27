package com.example.pamsbackend.securtiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


//    private final CustomUserDetailsService customUserDetailsService;
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//
//    public SecurityConfig(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.customUserDetailsService = customUserDetailsService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "https://pam-gui.gregtech.duckdns.org",
                                "http://localhost:5500",
                                "http://127.0.0.1:5500",
                                "http://192.168.77.230:443")
                        .allowedOriginPatterns(
                                "https://pam-gui.gregtech.duckdns.org/",
                                "https://pam-gui.gregtech.duckdns.org",
                                "http://192.168.77.230:443",

                                // DEV
                                "http://localhost:8586",
                                "http://localhost:8855",
                                "http://localhost:5500",
                                "localhost:8080",
                                "http://127.0.0.1:8586",
                                "http://127.0.0.1:8855",
                                "http://127.0.0.1:5500",
                                "http://127.0.0.1:8080"

                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders(
                                "Content-Type",
                                "Authorization",
                                "X-Requested-With",
                                "Accept",
                                "Origin",
                                "Access-Control-Allow-Origin",
                                "Access-Control-Request-Method",
                                "Access-Control-Request-Headers",
                                "multipart/form-data"




                        )
                        .exposedHeaders("Authorization")
                        .allowCredentials(true).maxAge(3600)
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

                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/index")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/hello")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/token/{id}")).hasRole("USER")



                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/login")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/users")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/downloadFile/{fileCode}/{username}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/downloadFile/{fileCode}/{username}/{galleryName}")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/file/{json}")).hasRole("USER")

                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/user/{id}")).hasRole("EDITUSER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/userPdf/{userId}")).hasRole("EDITUSER")
                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/user")).hasRole("EDITUSER")

                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/user")).hasRole("NEWUSER")

//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/notes")).hasRole("EDITNOTE")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/note/{id}")).hasRole("EDITNOTE")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/note/{ids}")).hasRole("EDITNOTE")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/note")).hasRole("EDITNOTE")


//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/downloadFile/{fileCode}/{username}")).hasRole("USER")
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/uploadFile")).hasRole("USER")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/file/{ids}")).hasRole("UPLOAD")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/uploadFile")).hasRole("UPLOAD")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/uploadToGallery")).hasRole("UPLOAD")

                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/item")).hasRole("EDITITEM")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/item/{id}")).hasRole("EDITITEM")
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/items/{ids}")).hasRole("EDITITEM")
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/item")).hasRole("EDITITEM")




                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/userstatus/**")).hasRole("STATUSCHECK")

                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v*/registration/confirm/**")).permitAll()
        );
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults())
//                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
//                        authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
