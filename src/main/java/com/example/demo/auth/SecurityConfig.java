package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    //@Autowired

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private FirebaseAuthFilter firebaseAuthFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                .requestMatchers("/docs/**").permitAll()
                .requestMatchers("/files/**").permitAll()
                .requestMatchers("/ofertas/**").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customAuthenticationEntryPoint)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
