package com.coordinacioncafesystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

import org.springframework.web.cors.*;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // 🔹 Permitir preflight CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 🔹 Endpoints públicos específicos (del código nuevo)
                        .requestMatchers(HttpMethod.POST, "/api/asistencia/solicitar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/asistencia/pendientes").permitAll()
                        .requestMatchers("/api/transferencias/**").permitAll()
                        .requestMatchers("/api/capacitaciones/**").permitAll()
                        .requestMatchers("/api/roles/**").permitAll()
                        .requestMatchers("/api/inscripciones/**").permitAll()
                        .requestMatchers("/api/colaboraciones/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/galeria/tecnologico/**").permitAll()

                        // 🔹 Estructura original (la dejamos intacta)
                        .requestMatchers(
                                "/api/auth/**",
                                "/api/public/**",
                                "/api/media/view/**",
                                "/api/media/download/**",
                                "/api/media/por-proyecto/**",
                                "/api/media/upload/**",
                                "/api/proyectos/**",
                                "/api/estadisticas/**",
                                "/public/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/uploads/**",
                                "/api/eventos/latest/**",
                                "/api/proyectos/latest**",
                                "/api/participantes/**",
                                "/api/tecnologicos/**",
                                "/api/asistencia/**",
                                "/api/media/**"
                        ).permitAll()

                        // 🔐 EVENTOS PROTEGIDOS
                        .requestMatchers(HttpMethod.GET, "/api/eventos/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/eventos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/eventos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOriginPatterns(List.of(
                "http://localhost:4200",
                "http://127.0.0.1:4200",
                "http://192.168.*:4200",
                "https://adicam.cloud",
                "https://www.adicam.cloud"
        ));
        c.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        c.setAllowedHeaders(List.of("*"));
        c.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", c);
        return src;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
