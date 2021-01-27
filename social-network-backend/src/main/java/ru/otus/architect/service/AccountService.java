package ru.otus.architect.service;

import ru.otus.architect.data.model.Account;

import java.util.List;

public interface AccountService {

    Account createAccount(Account account);

    Account getAccountByLogin(String login);

    Account getAccountById(Long id);

    List<Account> getAccountsByIds(List<Long> ids);

    List<Account> getAllAccounts();

}
