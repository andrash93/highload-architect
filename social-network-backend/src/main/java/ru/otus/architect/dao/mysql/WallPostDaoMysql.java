package ru.otus.architect.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.architect.dao.WallPostDao;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.exception.post.CreateWallPostException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class WallPostDaoMysql implements WallPostDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public WallPostDaoMysql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public WallPost addWallPost(WallPost post) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("userId", post.getUserId());
        params.addValue("dateCreated", post.getDateCreated());
        params.addValue("userName", post.getFullUserName());
        params.addValue("text", post.getText());

        String sql = "insert into SocialNetwork.posts (user_id, user_name,  date_created, text)" +
                " values (:userId, :userName, :dateCreated, :text)";

        KeyHolder kh = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(sql, params, kh);
            post.setId(kh.getKey().longValue());
            return post;
        } catch (Exception e) {
            log.error("ERROR in addWallPost ", e);
            throw new CreateWallPostException(e);
        }
    }

    @Override
    public List<WallPost> getWallPosts(Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        String sql = "select posts.id, posts.user_id, posts.user_name, posts.date_created, posts.text from SocialNetwork.posts" +
                " join follows on posts.user_id = follows.follower_id" +
                " where follows.user_id = :userId LIMIT 1000";
        try {
            return jdbcTemplate.query(sql, params, new WallPostDaoMysql.WallPostMapper());
        } catch (Exception e) {
            log.error("ERROR in getWallPosts ", e);
            throw new RuntimeException(e);
        }
    }

    private static class WallPostMapper implements RowMapper<WallPost> {
        @Override
        public WallPost mapRow(ResultSet resultSet, int i) throws SQLException {
            WallPost wallPost = new WallPost();
            wallPost.setId(resultSet.getLong("id"));
            wallPost.setUserId(resultSet.getLong("user_id"));
            wallPost.setFullUserName(resultSet.getString("user_name"));
            wallPost.setText(resultSet.getString("text"));
            wallPost.setDateCreated(resultSet.getTimestamp("date_created"));
            return wallPost;
        }
    }
}
