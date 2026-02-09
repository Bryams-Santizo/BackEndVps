package com.coordinacioncafesystem.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key getKey(){ return Keys.hmacShaKeyFor(jwtSecret.getBytes()); }

    public String generateToken(UserDetails userDetails){
        Date now = new Date();
        Date exp = new Date(now.getTime()+ jwtExpirationMs);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex){
            return false;
        }
    }

    private boolean isTokenExpired(String token){
        Date exp = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody().getExpiration();
        return exp.before(new Date());
    }
}
