package com.realestate.security;

import com.realestate.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(
            AuthenticationConfiguration config)
            throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain
    securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/user/login",
                                "/api/user/register",
                                "/api/user/verify-email"
                        ).permitAll()

                        // Projects
                        .requestMatchers(
                                "/api/projects",
                                "/api/projects/**"
                        ).permitAll()

                        // Buildings
                        .requestMatchers(
                                "/api/buildings",
                                "/api/buildings/**"
                        ).permitAll()

                        // Floors
                        .requestMatchers(
                                "/api/floors",
                                "/api/floors/**"
                        ).permitAll()

                        // Shop Units
                        .requestMatchers(
                                "/api/shopunits",
                                "/api/shopunits/**"
                        ).permitAll()

                        // Lease Requests
                        .requestMatchers(
                                "/api/leases",
                                "/api/leases/**"
                        ).hasAnyRole("USER",
                                "ADMIN")

                        // Purchase Requests
                        .requestMatchers(
                                "/api/purchaserequests",
                                "/api/purchaserequests/**"
                        ).hasAnyRole("USER",
                                "ADMIN")

                        // Admin APIs
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
