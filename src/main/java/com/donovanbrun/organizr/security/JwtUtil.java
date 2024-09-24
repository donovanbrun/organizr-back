package com.donovanbrun.organizr.security;

import com.donovanbrun.organizr.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtil {

    @Value("${jwt_secret}")
    private String SECRET_KEY;

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims.SUBJECT, String.class);
    }

    public <T> T extractClaim(String token, String claimName, Class<T> type) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName, type);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public String generateToken(User user) {
        return generateToken(Map.of(), user);
    }

    public String generateToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(getSecretKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String token, String email) {
        final String username = extractUsername(token);
        return username.equals(email) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
