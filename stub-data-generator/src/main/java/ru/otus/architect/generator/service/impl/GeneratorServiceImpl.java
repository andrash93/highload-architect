package ru.otus.architect.generator.service.impl;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.architect.generator.dao.GeneratorDao;
import ru.otus.architect.generator.data.Account;
import ru.otus.architect.generator.data.Auth;
import ru.otus.architect.generator.service.GeneratorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorDao generatorDao;
    private final PasswordEncoder passwordEncoder;
    private final ExecutorService executorService;

    public GeneratorServiceImpl(GeneratorDao generatorDao) {
        this.generatorDao = generatorDao;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.executorService = Executors.newFixedThreadPool(16);
    }

    @Override
    public void generateStubAccounts(int count) {
        executorService.execute(new AccountGenerator(count));
    }

    private class AccountGenerator implements Runnable {
        private final int count;

        private AccountGenerator(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            List<Account> accounts = new ArrayList<>();
            List<Auth> auths = new ArrayList<>();

            Faker faker = new Faker(new Locale("ru-RU"));
            FakeValuesService fakeValuesService = new FakeValuesService(
                    new Locale("en-US"), new RandomService());
            long startTime = System.currentTimeMillis();
            log.info("start generate accounts {}", count);
            for (int i = 0; i < count; i++) {
                Account account = generateAccount(faker, fakeValuesService);
                accounts.add(account);
                Auth auth = generateAuth(account.getLogin(), fakeValuesService);
                auths.add(auth);
            }
            log.info("finish generate accounts {} time {}", count, (System.currentTimeMillis() - startTime) / 1000);
            generatorDao.createAccounts(accounts);
            generatorDao.createAuths(auths);
        }

        private Account generateAccount(Faker faker, FakeValuesService fakeValuesService) {
            Account account = new Account();
            account.setLogin(fakeValuesService.bothify("????##???@gmail.com"));
            account.setName(faker.name().firstName());
            account.setSurname(faker.name().lastName());
            account.setCity(faker.address().city());
            account.setGender(faker.demographic().sex());
            account.setAge(faker.number().numberBetween(1, 99));
            return account;
        }

        private Auth generateAuth(String login, FakeValuesService fakeValuesService) {
            Auth auth = new Auth();
            auth.setLogin(login);
            auth.setRole("USER");
            auth.setPassword(passwordEncoder.encode(fakeValuesService.bothify("????##???")));
            return auth;
        }
    }
}
