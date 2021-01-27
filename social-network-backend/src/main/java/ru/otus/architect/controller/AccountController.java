package ru.otus.architect.controller;


import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.architect.controller.utils.AccountControllerUtils;
import ru.otus.architect.data.dto.account.AccountInfoResponse;
import ru.otus.architect.data.dto.account.AccountRegisterRequest;

import ru.otus.architect.data.dto.account.AccountRegisterResponse;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.AccountFriend;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.service.AccountService;
import ru.otus.architect.service.AuthService;
import ru.otus.architect.service.FriendService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class AccountController {

    private final AccountService accountService;
    private final FriendService friendService;
    private final AuthService authService;

    public AccountController(AccountService accountService,
                             FriendService friendService,
                             AuthService authService) {
        this.accountService = accountService;
        this.friendService = friendService;
        this.authService = authService;
    }

    @PostMapping("/account/register")
    @ResponseStatus(HttpStatus.OK)
    @Timed(value = "app.controller.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public AccountRegisterResponse registerAccount(@RequestBody AccountRegisterRequest accountRegisterRequest) {
        log.info("invoke registerAccount {}", accountRegisterRequest);

        createAuth(accountRegisterRequest);
        Account account = createAccount(accountRegisterRequest);
        String token = generateToken(account);

        return AccountControllerUtils.getAccountRegisterResponse(account, token);
    }

    @GetMapping("/account/current")
    public AccountInfoResponse accountInfo(Auth auth) {
        log.info("currentAccountId {} invoke account current info", auth.getUserId());

        Account account = accountService.getAccountById(auth.getUserId());
        return AccountControllerUtils.getAccountInfoResponse(account);
    }

    @GetMapping("/account/info/{accountId}")
    public AccountInfoResponse accountInfo(@PathVariable Long accountId, Auth auth) {
        log.info("currentAccountId {} invoke accountInfo {}", auth.getUserId(), accountId);

        Account account = accountService.getAccountById(accountId);
        AccountFriend accountFriend = friendService.getFriendStatus(account.getId(), auth.getUserId());

        return AccountControllerUtils.getAccountInfoResponse(account, accountFriend);
    }

    @GetMapping("/account/all")
    public List<AccountInfoResponse> getAllAccount(Auth auth) {
        log.info("currentAccountId {} invoke getAllAccount", auth.getUserId());
        List<AccountInfoResponse> response = new ArrayList<>();
        List<Account> allAccounts = accountService.getAllAccounts();
        allAccounts.forEach(account -> response.add(AccountControllerUtils.getAccountInfoResponse(account)));
        return response;
    }

    private Auth createAuth(AccountRegisterRequest accountRegisterRequest) {
        Auth auth = AccountControllerUtils.extractAuthUser(accountRegisterRequest);
        return authService.createAuth(auth);
    }

    private Account createAccount(AccountRegisterRequest accountRegisterRequest) {
        Account registerAccount = AccountControllerUtils.extractAccount(accountRegisterRequest);
        return accountService.createAccount(registerAccount);
    }

    private String generateToken(Account account) {
        return authService.generateToken(account.getLogin(), account.getId());
    }
}
