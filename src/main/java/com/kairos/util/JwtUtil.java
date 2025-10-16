package com.kairos.util;

import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @author kaijiang
 * @date 2025/10/11 16:20
 * @description
 */
@Component
@Data
public class JwtUtil {

    private final SecretKey secretKey;

    private final long expirationTime;

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey,
                   @Value("${jwt.expiration}") long expirationTime) {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.expirationTime = expirationTime;
    }

    public String generateToken(String userId) {
        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public String extractUserId(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
