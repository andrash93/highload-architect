package ru.otus.architect.generator.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import ru.otus.architect.generator.dao.GeneratorDao;
import ru.otus.architect.generator.data.Account;
import ru.otus.architect.generator.data.Auth;

import java.util.List;

@Repository
@Slf4j
public class GeneratorDaoImpl implements GeneratorDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GeneratorDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createAccounts(List<Account> accounts) {
        log.info("start account butch insert");
        String sql = "insert into SocialNetwork.users (login, name, surname, gender, age, city) " +
                "values (:login, :name, :surname, :gender, :age, :city)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(accounts.toArray());
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, batch);
        log.info("finish account butch insert");
    }

    @Override
    public void createAuths(List<Auth> auths) {
        log.info("start auth butch insert");
        String sql = "insert into SocialNetwork.auth (login, password, role) " +
                "values (:login, :password, :role)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(auths.toArray());
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, batch);
        log.info("finish auth butch insert");
    }
}
