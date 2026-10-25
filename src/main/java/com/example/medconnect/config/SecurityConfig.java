package com.example.medconnect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.medconnect.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

                // Doctors: dekhna sabke liye, manage sirf ADMIN
                .requestMatchers(HttpMethod.GET, "/api/doctors/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/doctors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/doctors/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasRole("ADMIN")

                // Medicines: dekhna sabke liye, manage sirf ADMIN
                .requestMatchers(HttpMethod.GET, "/api/medicines/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/medicines/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/medicines/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/medicines/**").hasRole("ADMIN")

                // Appointments
                .requestMatchers(HttpMethod.POST, "/api/appointments").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/api/appointments/my").hasRole("PATIENT")
                .requestMatchers(HttpMethod.PUT, "/api/appointments/*/cancel").hasRole("PATIENT")
                .requestMatchers(HttpMethod.GET, "/api/appointments/doctor").hasRole("DOCTOR")
                .requestMatchers(HttpMethod.PUT, "/api/appointments/*/status").hasRole("DOCTOR")
                .requestMatchers(HttpMethod.GET, "/api/appointments").hasRole("ADMIN")

                // Admin-only
                .requestMatchers("/api/admin/**").hasRole("ADMIN")

                // Baaki sab ke liye login zaroori
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}