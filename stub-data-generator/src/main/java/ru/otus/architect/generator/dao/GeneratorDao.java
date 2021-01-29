package ru.otus.architect.generator.dao;

import ru.otus.architect.generator.data.Account;
import ru.otus.architect.generator.data.Auth;

import java.util.List;

public interface GeneratorDao {
    void createAccounts(List<Account> accounts);

    void createAuths(List<Auth> auths);
}
