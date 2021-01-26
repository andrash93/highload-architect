package ru.otus.architect.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.architect.dao.AccountDao;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.exception.account.AccountNotFoundException;
import ru.otus.architect.exception.account.CreateAccountException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class AccountDaoMySql implements AccountDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AccountDaoMySql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account addAccount(Account user) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("name", user.getName());
        params.addValue("login", user.getLogin());
        params.addValue("surname", user.getSurname());
        params.addValue("gender", user.getGender());
        params.addValue("age", user.getAge());
        params.addValue("city", user.getCity());

        String sql = "insert into SocialNetwork.users (login, name, surname, gender, age, city) " +
                "values (:login, :name, :surname, :gender, :age, :city)";
        KeyHolder kh = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, params, kh);
            user.setId(kh.getKey().longValue());
            return user;
        } catch (Exception e) {
            log.error("ERROR in addUser ", e);
            throw new CreateAccountException(e);
        }
    }

    @Override
    public Account findAccountByLogin(String login) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("login", login);
        String sql = "select id, login, name, surname, gender, age, city from SocialNetwork.users where login = :login";
        try {
            return jdbcTemplate.queryForObject(sql, params, new AccountDaoMySql.AccountMapper());
        } catch (Exception e) {
            log.error("ERROR in findAccountByLogin ", e);
            throw new AccountNotFoundException(e);
        }
    }

    @Override
    public Account findAccountById(Long id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        String sql = "select id, login, name, surname, gender, age, city from SocialNetwork.users where id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, params, new AccountDaoMySql.AccountMapper());
        } catch (Exception e) {
            log.error("ERROR in findAccountById ", e);
            throw new AccountNotFoundException(e);
        }
    }

    @Override
    public List<Account> findAccountsByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            log.info("findAccountsByIds ids is empty, return Collections.emptyList()");
            return Collections.emptyList();
        }
        StringBuilder sql = new StringBuilder("select id, login, name, surname, gender, age, city from SocialNetwork.users where id IN (");
        for (Long id : ids) {
            sql.append(id).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        try {
            return jdbcTemplate.query(sql.toString(), new AccountDaoMySql.AccountMapper());
        } catch (Exception e) {
            log.error("ERROR in findAccountsByIds ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        String sql = "select id, login, name, surname, gender, age, city from SocialNetwork.users";
        try {
            return jdbcTemplate.query(sql, new AccountMapper());
        } catch (Exception e) {
            log.error("ERROR in getAllUsers ", e);
            return Collections.emptyList();
        }
    }


    private static class AccountMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account account = new Account();
            account.setId(resultSet.getLong("id"));
            account.setLogin(resultSet.getString("login"));
            account.setName(resultSet.getString("name"));
            account.setSurname(resultSet.getString("surname"));
            account.setGender(resultSet.getString("gender"));
            account.setAge(resultSet.getInt("age"));
            account.setCity(resultSet.getString("city"));
            return account;
        }
    }
}
