package com.users.users.security;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.api.key.secret}")
    private String validApiKey;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        APIKeyAuthenticationFilter apiKeyFilter = new APIKeyAuthenticationFilter(new AntPathRequestMatcher("/users/**"));
        apiKeyFilter.setAuthenticationManager(authentication -> {
            String apiKey = (String) authentication.getPrincipal();
            if (validApiKey.equals(apiKey)) {
                return new APIKeyAuthenticationToken(apiKey, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            } else {
                authentication.setAuthenticated(false);
            }
            return authentication;
        });
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> {
                    // if I don't permitAll, I can't get the swagger to work
                    authorize.anyRequest().permitAll();
                }
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
            )
            .addFilterBefore(apiKeyFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}