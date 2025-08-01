package com.example.coursemanagement.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh",
                                "/api/v1/learners",
                                "/api/v1/courses/*",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/learners").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/admins/*").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admins/*").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/admins/*").hasRole("Admin")
                        .requestMatchers(HttpMethod.POST, "/api/v1/courses").hasRole("Admin")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/courses/*").hasRole("Admin")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/*").hasRole("Admin")

                        .requestMatchers(HttpMethod.GET, "/api/v1/learners/*").hasRole("Learner")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/learners/*").hasRole("Learner")
                        .requestMatchers(HttpMethod.GET, "/api/v1/learners/*/courses").hasRole("Learner")
                        .requestMatchers(HttpMethod.GET, "/api/v1/submissions/*").hasRole("Learner")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
