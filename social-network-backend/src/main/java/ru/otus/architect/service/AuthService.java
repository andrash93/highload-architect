package ru.otus.architect.service;

import ru.otus.architect.data.model.Auth;

public interface AuthService {

    Auth createAuth(Auth auth);

    Auth getAuth(String login);

    void authenticate(Auth auth);

    String generateToken(String login, Long accountId);

    String refreshToken(String login, Long accountId);

    Long extractAuthId(String jwtToken);

    String extractAuthLogin(String jwtToken);
}
