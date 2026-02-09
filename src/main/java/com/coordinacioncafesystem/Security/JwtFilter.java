package com.coordinacioncafesystem.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UsuarioDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }





    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // 🔓 RUTAS PÚBLICAS: el filtro NO debe intentar validar JWT aquí
        if (uri.startsWith("/api/public/")
                || uri.startsWith("/api/media/view/")
                || uri.startsWith("/api/media/download/")
                || uri.startsWith("/v3/api-docs")

                || uri.startsWith("/swagger-ui")) {

            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            // sin token → simplemente deja pasar; luego SecurityConfig decide si requiere auth
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        String correo = null;
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
                        new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }

}

