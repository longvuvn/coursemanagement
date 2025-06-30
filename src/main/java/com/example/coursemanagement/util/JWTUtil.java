package com.example.coursemanagement.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

    public String generateToken(String username, long expiration) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key)
                .compact();
    }

    public String generateAccessToken(String username) {
        return generateToken(username, jwtAccessExpiration);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtRefreshExpiration);
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
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
