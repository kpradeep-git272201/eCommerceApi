package com.ecommerce.jwt.config.security;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.jwt.filter.JwtAuthenticationEntryPoint;
import com.ecommerce.jwt.filter.JwtFilter;


@Configuration
@AllArgsConstructor
public class WebSecurity {
	
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtFilter filter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return 
        		security.csrf(csrf-> csrf.disable())
                .cors(cors-> cors.disable())
                .authorizeHttpRequests(auth->auth
                		.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                		.requestMatchers("/authenticate").permitAll()
                		.requestMatchers("/public/**").permitAll()
                		.requestMatchers("/api/**").permitAll()
                		.anyRequest().authenticated())
                .exceptionHandling(ex ->ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

