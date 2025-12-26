package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Set;

public class JwtTokenProvider {

    // Secret key (fixed for tests)
    private static final String SECRET = "secret-key-for-tests-secret-key-for-tests";

    private static final long EXPIRATION = 3600000; // 1 hour

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * Create JWT token
     */
    public String createToken(Long userId, String email, Set<String> roles) {

        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Extract email
     */
    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    /**
     * Extract userId
     */
    public Long getUserId(String token) {
        Object value = getClaims(token).get("userId");
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        }
        return (Long) value;
    }

    /**
     * Extract roles
     */
    @SuppressWarnings("unchecked")
    public Set<String> getRoles(String token) {
        return (Set<String>) getClaims(token).get("roles");
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
