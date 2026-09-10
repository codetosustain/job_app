package com.ananya.jobportal.config;

import com.ananya.jobportal.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // Authentication
                        .requestMatchers("/auth/**").permitAll()

                        // Jobs - everyone logged in can view
                        .requestMatchers(HttpMethod.GET, "/jobs/**")
                        .hasAnyRole("CANDIDATE", "RECRUITER", "ADMIN")

                        // Job management - COMPANY and ADMIN
                        .requestMatchers(HttpMethod.POST, "/jobs/**")
                        .hasAnyRole("RECRUITER", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/jobs/**")
                        .hasAnyRole("RECRUITER", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/jobs/**")
                        .hasAnyRole("RECRUITER", "ADMIN")

                        // Candidate profile
                        .requestMatchers("/candidate/**")
                        .hasAnyRole("CANDIDATE", "ADMIN")

                        // Apply for jobs - CANDIDATE only
                        .requestMatchers(HttpMethod.POST, "/applications")
                        .hasAnyRole("CANDIDATE", "ADMIN")

                        // Candidate's applications
                        .requestMatchers("/applications/candidate/**")
                        .hasAnyRole("CANDIDATE", "ADMIN")

                        // Recruiter viewing applications
                        .requestMatchers("/applications/job/**")
                        .hasAnyRole("RECRUITER", "ADMIN")

                        // Recruiter changing application status
                        .requestMatchers(HttpMethod.PUT, "/applications/*/status")
                        .hasAnyRole("RECRUITER", "ADMIN")

                        // Individual application
                        .requestMatchers(HttpMethod.GET, "/applications/**")
                        .hasAnyRole("CANDIDATE", "RECRUITER", "ADMIN")

                        // Everything else
                        .anyRequest().authenticated()
                )

                // Run JWT filter before Spring's authentication filter
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}