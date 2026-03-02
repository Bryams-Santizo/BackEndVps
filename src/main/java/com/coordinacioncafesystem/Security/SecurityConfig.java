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
            .authorizeHttpRequests(auth -> auth

                // Preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // =====================
                // ✅ RUTAS PÚBLICAS
                // =====================

                // Auth público
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/bolsa/**").permitAll()

                // ✅ NUEVAS RUTAS PÚBLICAS (de la segunda config)
                .requestMatchers("/api/capacitaciones/**").permitAll()
                .requestMatchers("/api/roles/**").permitAll()
                .requestMatchers("/api/inscripciones/**").permitAll()
                .requestMatchers("/api/colaboraciones/**").permitAll()
                .requestMatchers("/api/certificaciones/**").permitAll()
                .requestMatchers("/api/productores/**").permitAll()
                .requestMatchers("/api/evaluaciones/**").permitAll()

                // API pública / docs
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()

                // Proyectos públicos
                .requestMatchers(HttpMethod.GET, "/api/proyectos/latest").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/proyectos/latest/**").permitAll()

                // Participantes públicos
                .requestMatchers(HttpMethod.GET, "/api/participantes/**").permitAll()

                // Galería pública
                .requestMatchers(HttpMethod.GET, "/api/galeria/**").permitAll()
                // ✅ Nuevo (más específico, de la segunda config)
                .requestMatchers(HttpMethod.GET, "/api/galeria/tecnologico/**").permitAll()

                // Eventos latest público
                .requestMatchers(HttpMethod.GET, "/api/eventos/latest").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/eventos/latest/**").permitAll()

                // Tecnológicos / asistencia / transferencias / estadísticas
                .requestMatchers("/api/tecnologicos/**").permitAll()
                .requestMatchers("/api/asistencia/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/asistencia/solicitar").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/asistencia/pendientes").permitAll()
                .requestMatchers("/api/transferencias/**").permitAll()
                .requestMatchers("/api/estadisticas/**").permitAll()

                // ✅ UPLOADS PÚBLICOS
                .requestMatchers(HttpMethod.GET,  "/api/uploads/**").permitAll()
                .requestMatchers(HttpMethod.HEAD, "/api/uploads/**").permitAll()
                .requestMatchers(HttpMethod.GET,  "/uploads/**").permitAll()
                .requestMatchers(HttpMethod.HEAD, "/uploads/**").permitAll()
                // (en la segunda config también estaba /uploads/** permitAll; ya queda cubierto)

                // ✅ MEDIA PÚBLICO (vista/descarga)
                .requestMatchers(HttpMethod.GET,  "/api/media/**").permitAll()
                .requestMatchers(HttpMethod.HEAD, "/api/media/**").permitAll()

                // =====================
                // 🔒 RUTAS PROTEGIDAS
                // =====================

                // Media privado (upload/editar/borrar)
                .requestMatchers(HttpMethod.POST,   "/api/media/**").authenticated()
                .requestMatchers(HttpMethod.PUT,    "/api/media/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/media/**").authenticated()

                // Proyectos privados
                .requestMatchers(HttpMethod.POST,   "/api/proyectos/**").authenticated()
                .requestMatchers(HttpMethod.PUT,    "/api/proyectos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/proyectos/**").authenticated()

                // Eventos (CRUD protegido)
                .requestMatchers(HttpMethod.GET,    "/api/eventos/**").authenticated()
                .requestMatchers(HttpMethod.POST,   "/api/eventos/**").authenticated()
                .requestMatchers(HttpMethod.PUT,    "/api/eventos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").authenticated()

                // ✅ NUEVO: Galería (CRUD protegido) como en la segunda config
                .requestMatchers(HttpMethod.POST,   "/api/galeria/**").authenticated()
                .requestMatchers(HttpMethod.PUT,    "/api/galeria/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/galeria/**").authenticated()

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
