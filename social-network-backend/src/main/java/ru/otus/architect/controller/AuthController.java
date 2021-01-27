package ru.otus.architect.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.data.dto.auth.AuthRequest;
import ru.otus.architect.data.dto.auth.AuthResponse;
import ru.otus.architect.service.AccountService;
import ru.otus.architect.service.AuthService;


@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    public AuthController(AuthService authService, AccountService accountService) {
        this.authService = authService;
        this.accountService = accountService;
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse authenticate(@RequestBody AuthRequest authRequest) {
        authService.authenticate(new Auth(authRequest.getName(), authRequest.getPassword()));
        Account account = accountService.getAccountByLogin(authRequest.getName());
        log.info("currentAccountId {} invoke authenticate", account.getId());
        String token = authService.generateToken(account.getLogin(), account.getId());
        return new AuthResponse(token, account.getId());
    }

    @GetMapping("/authenticate/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refreshAuthenticationToken(Auth auth) {
        log.info("currentAccountId {} invoke refreshAuthenticationToken", auth.getUserId());
        Account account = accountService.getAccountById(auth.getUserId());
        String jwtToken = authService.refreshToken(account.getLogin(), account.getId());
        return new AuthResponse(jwtToken, account.getId());
    }

}
