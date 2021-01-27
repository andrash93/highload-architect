package ru.otus.architect.controller.utils;

import ru.otus.architect.data.dto.account.AccountInfoResponse;
import ru.otus.architect.data.dto.account.AccountRegisterRequest;
import ru.otus.architect.data.dto.account.AccountRegisterResponse;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.AccountFriend;
import ru.otus.architect.data.model.Auth;

public class AccountControllerUtils {

    public static AccountInfoResponse getAccountInfoResponse(Account account) {
        AccountInfoResponse response = new AccountInfoResponse();
        response.setId(account.getId());
        response.setLogin(account.getLogin());
        response.setName(account.getName());
        response.setSurname(account.getSurname());
        response.setCity(account.getCity());
        response.setGender(account.getGender());
        response.setAge(account.getAge());
        return response;
    }

    public static AccountInfoResponse getAccountInfoResponse(Account account, AccountFriend accountFriend) {
        AccountInfoResponse response = getAccountInfoResponse(account);
        response.setAccountFriendStatus(accountFriend);
        return response;
    }

    public static AccountRegisterResponse getAccountRegisterResponse(Account account, String token) {
        AccountRegisterResponse accountRegisterResponse = new AccountRegisterResponse();
        accountRegisterResponse.setId(account.getId());
        accountRegisterResponse.setLogin(account.getLogin());
        accountRegisterResponse.setName(account.getName());
        accountRegisterResponse.setStatus("success");
        accountRegisterResponse.setJwtToken(token);
        return accountRegisterResponse;
    }

    public static Auth extractAuthUser(AccountRegisterRequest accountRegisterRequest) {
        Auth auth = new Auth();
        auth.setLogin(accountRegisterRequest.getLogin());
        auth.setPassword(accountRegisterRequest.getPassword());
        auth.setRole("USER");
        return auth;
    }

    public static Account extractAccount(AccountRegisterRequest accountRegisterRequest) {
        Account account = new Account();
        account.setLogin(accountRegisterRequest.getLogin());
        account.setName(accountRegisterRequest.getName());

        account.setSurname(accountRegisterRequest.getSurname());
        account.setCity(accountRegisterRequest.getCity());
        account.setGender(accountRegisterRequest.getGender());
        account.setAge(accountRegisterRequest.getAge());
        return account;
    }
}
