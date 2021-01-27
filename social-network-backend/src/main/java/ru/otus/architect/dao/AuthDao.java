package ru.otus.architect.dao;

import ru.otus.architect.data.model.Auth;

public interface AuthDao {

    Auth findAuthByLogin(String login);

    Auth createAuth(Auth auth);

}
