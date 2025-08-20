package com.forohub.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    public String generarToken(String email) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.builder()
                    .setIssuer(issuer)
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(generarFechaExpiracion())
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception exception) {
            throw new RuntimeException("Error al generar token jwt", exception);
        }
    }

    public String getSubject(String jwtToken) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken)
                    .getBody()
                    .getSubject();
        } catch (Exception exception) {
            throw new RuntimeException("Token JWT inválido o expirado!");
        }
    }

    private Date generarFechaExpiracion() {
        return Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00")));
    }
}
