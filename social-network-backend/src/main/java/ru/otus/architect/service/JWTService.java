package ru.otus.architect.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JWTService {

    String extractLogin(String token);

    Long extractId(String token);

    Date extractExpiration(String token);

    String generateToken(String login, Long accountId);

    Boolean validateToken(String token, UserDetails userDetails);

}
