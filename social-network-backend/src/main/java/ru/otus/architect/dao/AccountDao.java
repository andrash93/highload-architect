package ru.otus.architect.dao;

import ru.otus.architect.data.model.Account;

import java.util.List;

public interface AccountDao {

    Account addAccount(Account account);

    Account findAccountByLogin(String login);

    Account findAccountById(Long id);

    List<Account> findAccountsByIds(List<Long> ids);

    List<Account> getAllAccounts();

}
