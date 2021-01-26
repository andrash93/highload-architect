package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.architect.dao.AccountDao;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.service.AccountService;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account createAccount(Account account) {
        return accountDao.addAccount(account);
    }

    @Override
    public Account getAccountByLogin(String login) {
        return accountDao.findAccountByLogin(login);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountDao.findAccountById(id);
    }

    @Override
    public List<Account> getAccountsByIds(List<Long> ids) {
        return accountDao.findAccountsByIds(ids);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

}
