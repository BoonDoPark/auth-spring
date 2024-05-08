package com.example.auth.global.utills;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtUtil {
    private final Long expiration;
    private final SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") Long expiration
    ) {
        this.expiration = expiration;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email) {
        String token = Jwts.builder().subject(email)
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(key)
                .compact();
        return token;
    }

    public String getByEmailFromTokenAndValidation(String token) {
        Claims payload = (Claims) Jwts.parser()
                .verifyWith(key)
                .build()
                .parse(token)
                .getPayload();

        return payload.getSubject();

    }
}
