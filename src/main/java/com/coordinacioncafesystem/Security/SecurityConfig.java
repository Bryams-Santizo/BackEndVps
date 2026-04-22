package com.coordinacioncafesystem.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth

                // =====================
                // PRE-FLIGHT
                // =====================
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // =====================
                // AUTH
                // =====================
                .requestMatchers("/api/auth/**").permitAll()

                // =====================
                // PUBLIC API
                // =====================
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/api/bolsa/**").permitAll()

                // =====================
                // SWAGGER / DOCS
                // =====================
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()

                // =====================
                // EVENTOS / PROYECTOS PÚBLICOS
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/eventos/latest/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/proyectos/latest").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/proyectos/latest/**").permitAll()

                // =====================
                // PARTICIPANTES / GALERÍA
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/participantes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/galeria/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/galeria/tecnologico/**").permitAll()

                // =====================
                // UPLOADS
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/uploads/**").permitAll()
                .requestMatchers(HttpMethod.HEAD, "/api/uploads/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()
                .requestMatchers(HttpMethod.HEAD, "/uploads/**").permitAll()

                // =====================
                // MEDIA
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/media/**").permitAll()
                .requestMatchers(HttpMethod.HEAD, "/api/media/**").permitAll()
                .requestMatchers("/api/media/view/**").permitAll()
                .requestMatchers("/api/media/download/**").permitAll()
                .requestMatchers("/api/media/por-proyecto/**").permitAll()

                // =====================
                // TECNOLÓGICOS / ASISTENCIA / TRANSFERENCIAS / ESTADÍSTICAS
                // =====================
                .requestMatchers("/api/tecnologicos/**").permitAll()
                .requestMatchers("/api/asistencia/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/asistencia/solicitar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/asistencia/pendientes").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/estadisticas/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/transferencias/**").permitAll()

                // =====================
                // CAPACITACIONES
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/capacitaciones/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/capacitaciones/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers(HttpMethod.PUT, "/api/capacitaciones/**").hasAnyRole("ADMIN", "COORDINADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/capacitaciones/**").hasAnyRole("ADMIN", "COORDINADOR")

                // =====================
                // OTRAS RUTAS PÚBLICAS
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/colaboraciones/**").permitAll()
                .requestMatchers("/api/roles/**").permitAll()
                .requestMatchers("/api/inscripciones/**").permitAll()
                .requestMatchers("/api/certificaciones/**").permitAll()
                .requestMatchers("/api/productores/**").permitAll()
                .requestMatchers("/api/evaluaciones/**").permitAll()

                // =====================
                // EVENTOS PRIVADOS
                // =====================
                .requestMatchers(HttpMethod.GET, "/api/eventos/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/eventos/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/eventos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").authenticated()

                // =====================
                // GALERÍA PRIVADA (CRUD)
                // =====================
                .requestMatchers(HttpMethod.POST, "/api/galeria/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/galeria/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/galeria/**").authenticated()

                // =====================
                // MEDIA PRIVADA (UPLOAD / EDICIÓN / BORRADO)
                // =====================
                .requestMatchers(HttpMethod.POST, "/api/media/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/media/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/media/**").authenticated()

                // =====================
                // PROYECTOS PRIVADOS (CRUD)
                // =====================
                .requestMatchers(HttpMethod.POST, "/api/proyectos/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/proyectos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/proyectos/**").authenticated()

                // =====================
                // RESTO
                // =====================
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration c = new CorsConfiguration();

        c.setAllowedOriginPatterns(List.of(
            "http://localhost:4200",
            "http://127.0.0.1:4200",
            "http://192.168.*:4200",
            "http://adicam.cloud",
            "https://adicam.cloud",
            "https://www.adicam.cloud"
        ));

        c.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        c.setAllowedHeaders(List.of("*"));
        c.setExposedHeaders(List.of("Content-Disposition"));
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
