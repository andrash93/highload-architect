package ru.otus.architect.exception.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
