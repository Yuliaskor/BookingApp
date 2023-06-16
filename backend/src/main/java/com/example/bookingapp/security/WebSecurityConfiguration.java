package com.example.bookingapp.security;

import com.example.bookingapp.security.filters.FilterChainExceptionHandler;
import com.example.bookingapp.security.filters.GoogleTokenFilter;
import com.example.bookingapp.security.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final FilterChainExceptionHandler filterChainExceptionHandler;
    private final GoogleAuthService googleAuthService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        GoogleTokenFilter googleTokenFilter = new GoogleTokenFilter(googleAuthService);

        http.cors()
                .and()
                .csrf()
                .disable();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(googleTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(filterChainExceptionHandler, GoogleTokenFilter.class);

        return http.build();
    }

}
