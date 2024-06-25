package com.duy.spring.demo.resources.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenParser {
    @Value("${jwt.token.symmetric.key}")
    private String secret;

    public Claims validateAndGetToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Set<SimpleGrantedAuthority> getRolesFromJWT(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        return roles.stream()
                .map(it -> new SimpleGrantedAuthority("ROLE_" + it))
                .collect(Collectors.toSet());
    }
}
