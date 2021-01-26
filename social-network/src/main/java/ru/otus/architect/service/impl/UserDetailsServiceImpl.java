package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.architect.dao.AuthDao;
import ru.otus.architect.data.model.Auth;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthDao authDao;

    public UserDetailsServiceImpl(AuthDao authDao) {
        this.authDao = authDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
        Auth auth = authDao.findAuthByLogin(userLogin);
        if (auth == null) {
            throw new UsernameNotFoundException("Unknown user: " + userLogin);
        }
        return User.builder()
                .username(auth.getLogin())
                .password(auth.getPassword())
                .roles(auth.getRole())
                .build();
    }
}
