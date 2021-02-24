package ru.otus.architect.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.architect.dao.FollowDao;
import ru.otus.architect.data.model.Follow;
import ru.otus.architect.exception.post.CreateFollowerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class FollowDaoMysql implements FollowDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FollowDaoMysql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFollow(Follow follow) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("userId", follow.getUserId());
        params.addValue("followerId", follow.getFollowerId());

        String sql = "insert into SocialNetwork.follows (user_id, follower_id)" +
                " values (:userId, :followerId)";

        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            log.error("ERROR in addFollow ", e);
            throw new CreateFollowerException(e);
        }
    }

    @Override
    public void deleteFollow(Follow follow) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("userId", follow.getUserId());
        params.addValue("followerId", follow.getFollowerId());

        String sql = "delete from SocialNetwork.follows "
                + " where (user_id = :userId and follower_id = :followerId) " +
                " or (user_id = :followerId and follower_id = :userId)";

        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<Follow> getFollowers(Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        String sql = "select user_id, follower_id from SocialNetwork.follows where user_id = :userId";
        try {
            return jdbcTemplate.query(sql, params, new FollowMapper());
        } catch (Exception e) {
            log.error("ERROR in getWallPosts ", e);
            throw new RuntimeException(e);
        }
    }

    private static class FollowMapper implements RowMapper<Follow> {
        @Override
        public Follow mapRow(ResultSet resultSet, int i) throws SQLException {
            Follow follow = new Follow();
            follow.setUserId(resultSet.getLong("user_id"));
            follow.setFollowerId(resultSet.getLong("follower_id"));
            return follow;
        }
    }
}
