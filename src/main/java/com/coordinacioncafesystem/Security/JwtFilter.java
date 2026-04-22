package com.coordinacioncafesystem.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioDetailsService userDetailsService;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public JwtFilter(JwtUtil jwtUtil, UsuarioDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // =====================
        // PREFLIGHT
        // =====================
        if ("OPTIONS".equalsIgnoreCase(method)) return true;

        // =====================
        // SWAGGER / DOCS
        // =====================
        if (matcher.match("/v3/api-docs/**", uri)) return true;
        if (matcher.match("/swagger-ui/**", uri)) return true;

        // =====================
        // AUTH / PÚBLICO BASE
        // =====================
        if (matcher.match("/api/auth/**", uri)) return true;
        if (matcher.match("/api/public/**", uri)) return true;
        if (matcher.match("/public/**", uri)) return true;
        if (matcher.match("/api/bolsa/**", uri)) return true;

        // =====================
        // EVENTOS / PROYECTOS PÚBLICOS
        // =====================
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/eventos/latest", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/eventos/latest/**", uri)) return true;

        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/proyectos/latest", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/proyectos/latest/**", uri)) return true;

        // =====================
        // CAPACITACIONES
        // =====================
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/capacitaciones/**", uri)) return true;

        // =====================
        // GALERÍA / PARTICIPANTES
        // =====================
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/galeria/**", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/galeria/tecnologico/**", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/participantes/**", uri)) return true;

        // =====================
        // COLABORACIONES / ESTADÍSTICAS / TRANSFERENCIAS
        // =====================
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/colaboraciones/**", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/estadisticas/**", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/transferencias/**", uri)) return true;

        // =====================
        // TECNOLÓGICOS / ASISTENCIA
        // =====================
        if (matcher.match("/api/tecnologicos/**", uri)) return true;
        if (matcher.match("/api/asistencia/**", uri)) return true;
        if ("POST".equalsIgnoreCase(method) && matcher.match("/api/asistencia/solicitar", uri)) return true;
        if ("GET".equalsIgnoreCase(method) && matcher.match("/api/asistencia/pendientes", uri)) return true;

        // =====================
        // OTRAS RUTAS PÚBLICAS
        // =====================
        if (matcher.match("/api/roles/**", uri)) return true;
        if (matcher.match("/api/inscripciones/**", uri)) return true;
        if (matcher.match("/api/certificaciones/**", uri)) return true;
        if (matcher.match("/api/productores/**", uri)) return true;
        if (matcher.match("/api/evaluaciones/**", uri)) return true;

        // =====================
        // UPLOADS
        // =====================
        if (("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method)) &&
            matcher.match("/api/uploads/**", uri)) return true;

        if (("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method)) &&
            matcher.match("/uploads/**", uri)) return true;

        // =====================
        // MEDIA PÚBLICA
        // =====================
        if (("GET".equalsIgnoreCase(method) || "HEAD".equalsIgnoreCase(method)) &&
            matcher.match("/api/media/**", uri)) return true;

        if (matcher.match("/api/media/view/**", uri)) return true;
        if (matcher.match("/api/media/download/**", uri)) return true;
        if (matcher.match("/api/media/por-proyecto/**", uri)) return true;

        return false;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        String correo;

        try {
            correo = jwtUtil.extractUsername(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails ud = userDetailsService.loadUserByUsername(correo);

            if (jwtUtil.validateToken(token, ud)) {

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                ud,
                                null,
                                ud.getAuthorities()
                        );

                auth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
