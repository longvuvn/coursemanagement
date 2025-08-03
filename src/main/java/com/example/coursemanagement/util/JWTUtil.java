package com.example.coursemanagement.util;

import com.example.coursemanagement.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.access-token}")
    private long jwtAccessExpiration;

    @Value("${jwt.expiration.refresh-token}")
    private long jwtRefreshExpiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    private String generateToken(User user, long expiration) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId().toString())
                .claim("fullName", user.getFullName())
                .claim("avatar", user.getAvatar())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateAccessToken(User user) {
        return generateToken(user, jwtAccessExpiration);
    }


    public String generateRefreshToken(User user) {
        return generateToken(user, jwtRefreshExpiration);
    }


    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // sẽ ném exception nếu token sai
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
