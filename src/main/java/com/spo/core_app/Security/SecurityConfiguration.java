package com.spo.core_app.Security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

    @Configuration
    public class SecurityConfiguration {

        public JwtFilter jwtFilter;

        @Autowired
        public SecurityConfiguration(JwtFilter jwtFilter){
            this.jwtFilter = jwtFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/api/v1/emp/login",
                                    "/api/v1/Company/Procurement-Company/register",
                                    "/api/v1/Company/pending",
                                    "/api/v1/Company/*/review"

                            ).permitAll()
                            .anyRequest().authenticated()
                    )

                    .sessionManagement(session ->
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )

                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
        @Bean
        public CorsConfigurationSource corsConfigurationSource() {

            CorsConfiguration configuration = new CorsConfiguration();

            configuration.setAllowedOrigins(List.of("http://localhost:5173"));

            configuration.setAllowedMethods(List.of(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "OPTIONS"
            ));

            configuration.setAllowedHeaders(List.of("*"));

            configuration.setAllowCredentials(true);

            UrlBasedCorsConfigurationSource source =
                    new UrlBasedCorsConfigurationSource();

            source.registerCorsConfiguration("/**", configuration);

            return source;
        }
    }

