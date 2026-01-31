package com.sevaqueue.security;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.expiration.time}")
    private long jwtExpirationTime;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey secretKey;

    // STEP 5.1 – create symmetric key
    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // STEP 5.2 – generate JWT
    public String generateToken(UserPrincipal principal) {

        return Jwts.builder()
                .subject(principal.getUsername())   // email
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .claims(Map.of(
                        "userId", principal.getUserId(),
                        "role", principal.getRole()
                ))
                .signWith(secretKey)
                .compact();
    }

    // STEP 5.3 – validate JWT
    public Claims validateToken(String token)
            throws JwtException, IllegalArgumentException {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
 // extract username (email) from token
    public String getUsernameFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    // build UserPrincipal from token
    public UserPrincipal getUserFromToken(String token) {
        Claims claims = validateToken(token);

        Long userId = claims.get("userId", Long.class);
        String roleStr = claims.get("role", String.class);

        return new UserPrincipal(
                userId,
                claims.getSubject(),
                com.sevaqueue.entity.Role.valueOf(roleStr)
        );
    }

}
