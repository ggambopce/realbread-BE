package com.jino.realbread.global.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey key;

    public JwtProvider(@Value("${secret-key}") String base64EncodedKey) {
        byte[] keyBytes = Base64.getDecoder().decode(base64EncodedKey); // Base64 디코딩
        this.key = Keys.hmacShaKeyFor(keyBytes); // HS256에 적합한 SecretKey 생성
    }

    public String create(String email) {
        Date expiredDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validate(String jwt) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return claims.getSubject();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}