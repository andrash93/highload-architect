package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.architect.dao.AuthDao;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.service.AuthService;
import ru.otus.architect.service.JWTService;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthDao authDao;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    public AuthServiceImpl(AuthDao authDao,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JWTService jwtService) {
        this.authDao = authDao;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Auth createAuth(Auth auth) {
        String encodePassword = passwordEncoder.encode(auth.getPassword());
        return authDao.createAuth(new Auth(auth.getLogin(), encodePassword, auth.getRole()));
    }


    @Override
    public Auth getAuth(String login) {
        return authDao.findAuthByLogin(login);
    }

    @Override
    public void authenticate(Auth auth) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    auth.getLogin(),
                    auth.getPassword()));
            log.info(authentication.toString());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Имя или пароль неправильны", e);
        }
    }

    @Override
    public String generateToken(String login, Long accountId) {
        final String jwt = jwtService.generateToken(login, accountId);
        log.info(jwt);
        return jwt;
    }

    @Override
    public String refreshToken(String login, Long accountId) {
        final String jwt = jwtService.generateToken(login, accountId);
        log.info(jwt);
        return jwt;
    }

    @Override
    public Long extractAuthId(String jwtToken) {
        return jwtService.extractId((jwtToken).substring(7));
    }

    @Override
    public String extractAuthLogin(String jwtToken) {
        return jwtService.extractLogin((jwtToken).substring(7));
    }
}
