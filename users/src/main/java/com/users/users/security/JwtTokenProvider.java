package com.users.users.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityLimitInMilliseconds = 3600000;

    public long getValidityLimitInMilliseconds() {
        return validityLimitInMilliseconds;
    }

    private String removeBearerPrefix(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        } else {
            throw new IllegalArgumentException("Token tem formato inválido!");
        }
    }

    public String createToken(String email, String profile) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityLimitInMilliseconds);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(validity)
                .claim("profile", profile)
                .signWith(getSigningKey())
                .compact();
    }

     private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try {
            token = removeBearerPrefix(token);
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }

    public LocalDateTime getExpirationFromToken(String token) {
        token = removeBearerPrefix(token);

        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
            
        return claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String getEmailFromToken(String token) {
        token = removeBearerPrefix(token);

        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return claims.getSubject();
    }

    public String getProfileFromToken(String token) {
        token = removeBearerPrefix(token);

        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return claims.get("profile", String.class);
    }

    public Map<String, Object> getEmailAndProfileFromToken(String token) {
        token = removeBearerPrefix(token);

        Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
        String email = claims.getSubject();
        String profile = (String) claims.get("profile");
        Map<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("profile", profile);
        return result;
    }
}
