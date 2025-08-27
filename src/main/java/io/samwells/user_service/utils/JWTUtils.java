package io.samwells.user_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.samwells.user_service.entity.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtils {
    private final SecretKey secretKey;

    public JWTUtils(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts
            .parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
