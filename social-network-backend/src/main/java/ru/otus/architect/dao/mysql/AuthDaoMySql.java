package ru.otus.architect.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.architect.dao.AuthDao;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.exception.account.CreateAccountException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@Repository
@Slf4j
public class AuthDaoMySql implements AuthDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthDaoMySql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Auth createAuth(Auth auth) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("login", auth.getLogin());
        params.addValue("password", auth.getPassword());
        params.addValue("role", auth.getRole());

        String sql = "insert into SocialNetwork.auth (login, password, role) " +
                "values (:login, :password, :role)";
        KeyHolder kh = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, params, kh);
            return auth;
        } catch (Exception e) {
            log.error("ERROR in createAuth ", e);
            throw new CreateAccountException(e);
        }
    }

    @Override
    public Auth findAuthByLogin(String login) {
        log.info("invoke findAuthByLogin {}", login);
        Map<String, Object> params = new HashMap<>(1);
        params.put("login", login);
        String sql = "select login, password, role from SocialNetwork.auth where login = :login";
        try {
            Auth auth = jdbcTemplate.queryForObject(sql, params, new AuthMapper());
            return auth;
        } catch (Exception e) {
            log.error("ERROR in findAuthByLogin ", e);
            throw new RuntimeException(e);
        }
    }

    private static class AuthMapper implements RowMapper<Auth> {
        @Override
        public Auth mapRow(ResultSet resultSet, int i) throws SQLException {
            Auth auth = new Auth();
            auth.setLogin(resultSet.getString("login"));
            auth.setPassword(resultSet.getString("password"));
            auth.setRole(resultSet.getString("role"));
            return auth;
        }
    }

}
