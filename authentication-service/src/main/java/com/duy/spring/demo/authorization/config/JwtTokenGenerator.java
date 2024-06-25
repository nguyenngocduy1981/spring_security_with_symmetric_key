package com.duy.spring.demo.authorization.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenGenerator {
    @Value("${jwt.token.symmetric.key}")
    private String secret;

    public String generate(String username, String roles) {
        List<String> roleList = Arrays.stream(roles.split(","))
                .map(it -> it.trim())
                .map(it -> it.toUpperCase())
                .collect(Collectors.toList());

        Instant now = Instant.now();
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .signWith(hmacKey)
                .issuedAt(new Date(now.toEpochMilli()))
                .expiration(new Date(now.plusSeconds(1000).toEpochMilli()))
                .issuer("Duy")
                .claim("username", username)
                .claim("ten", "My Org")
                .claim("scope", "ADMIN")
                .claim("roles", roleList)
                .compact();
    }
}
