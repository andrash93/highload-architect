package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.architect.dao.AccountDao;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.Follow;
import ru.otus.architect.service.AccountService;
import ru.otus.architect.service.FollowService;

import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;
    private final FollowService followService;

    public AccountServiceImpl(AccountDao accountDao,
                              FollowService followService) {
        this.accountDao = accountDao;
        this.followService = followService;
    }

    @Override
    public Account createAccount(Account account) {
        Account reg = accountDao.addAccount(account);
        followService.addFollow(new Follow(reg.getId(), reg.getId()));
        return reg;
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
    public List<Account> findByFirstNameAndLastName(String firstName, String lastName) {
        return accountDao.findByFirstNameAndLastName(firstName, lastName, 500);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts(500);
    }

}
