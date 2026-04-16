package com.backend.backend.service;

import com.backend.backend.model.Users;
import com.backend.backend.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    private static final String SECRET_KEY = "your_secret_code";
    private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(120);
    private final UserRepository repository;

    @Autowired
    public JwtService(UserRepository repository){
        this.repository = repository;
    }

    public String generateToken(UserDetails user){
        Users myUser = repository.findByEmail(user.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("username not found!!"));
        Map<String, String> claim = new HashMap<>();
        claim.put("name", myUser.getName());
        claim.put("userId", "" + myUser.getUserId());
        claim.put("role", "" + myUser.getRole());
        return Jwts.builder()
                .subject(user.getUsername())
                .claims(claim)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] key = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractEmail(String token){
        return extractClaims(token)
                .getSubject();
    }

    public boolean validateToken(String token){
        return extractClaims(token)
                .getExpiration()
                .after(Date.from(Instant.now()));
    }
}
