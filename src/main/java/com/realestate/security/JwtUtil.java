package com.realestate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final SecretKey secretKey =
            Jwts.SIG.HS512.key().build();


    private static final long JWT_EXPIRATION =
            1000 * 60 * 60 * 24; // 24 Hours

    public String generateToken(
            String email,
            String role) {

        Map<String, Object> claims =
                new HashMap<>();

        claims.put("role", role);
        claims.put("email", email);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(
                        System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject);
    }

    public String extractRole(
            String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token)
                .get("email", String.class);
    }

    public Date extractExpiration(
            String token) {

        return extractClaim(
                token,
                Claims::getExpiration);
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(
            String token,
            String username) {

        return username.equals(
                extractUsername(token))
                && !isTokenExpired(token);
    }

    public boolean isTokenExpired(
            String token) {

        return extractExpiration(token)
                .before(new Date());
    }

}
