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


    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private  CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;
    @Autowired
    public SecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customizeAuthenticationSuccessHandler = customizeAuthenticationSuccessHandler;
    }

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new CustomUserDetailsService();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

    }


    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http  .authorizeHttpRequests(configurer ->
                        configurer

                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/username").permitAll()

                );
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().permitAll())
            .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8585", "http://127.0.0.1:8585", "http://127.0.0.1:5500")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Authorization")
                        .allowCredentials(true)
                ;
            }
        };
    }


//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
//        http.authorizeHttpRequests(configurer ->
//                configurer
//
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/username")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/trips/{id}")).hasRole("USER")
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/trips")).hasRole("USER")
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/trips/{id}")).hasRole("USER")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/users")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/customers")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/customers/{id}")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/customers/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/trips/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/customers/{id}")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/v1/destination")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.DELETE, "/api/v1/destination/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.PUT, "/api/v1/destination/{id}")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/destination/{id}")).hasAnyRole("USER", "ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/alltrips")).hasRole("ADMIN")
//                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/v1/currency/{total}/{currancy}")).hasAnyRole("USER", "ADMIN")
//        );
//        http.httpBasic(Customizer.withDefaults());
//        http.cors(Customizer.withDefaults());
//        http.csrf(csrf -> csrf
//                .ignoringRequestMatchers(mvc.pattern("/h2-console/**"))
//                .disable());
//        http.headers().frameOptions().sameOrigin();
//        return http.build();
//    }

}
