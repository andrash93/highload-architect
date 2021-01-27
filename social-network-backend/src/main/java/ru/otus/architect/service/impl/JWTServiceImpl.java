package ru.otus.architect.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.otus.architect.service.JWTService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.sessionTime}")
    private long sessionTime;


    @Override
    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Long extractId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getId));
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(String login, Long accountId) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, login, accountId);
    }

    private String createToken(Map<String, Object> claims, String subject, Long accountId) {

        return Jwts.builder().setClaims(claims)
                .setSubject(subject).setId(String.valueOf(accountId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expireTimeFromNow())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractLogin(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Date expireTimeFromNow() {
        return new Date(System.currentTimeMillis() + sessionTime);
    }
}