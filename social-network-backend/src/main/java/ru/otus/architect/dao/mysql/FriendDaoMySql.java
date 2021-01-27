package ru.otus.architect.dao.mysql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.otus.architect.dao.FriendDao;
import ru.otus.architect.data.FriendRequest;
import ru.otus.architect.data.FriendStatus;
import ru.otus.architect.data.model.AccountFriend;
import ru.otus.architect.exception.friend.FriendStatusNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@Slf4j
public class FriendDaoMySql implements FriendDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FriendDaoMySql(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createFriend(AccountFriend accountFriend) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("sender_person_id", accountFriend.getSenderPersonId());
        params.addValue("receiver_person_id", accountFriend.getReceiverPersonId());
        params.addValue("creation_date", accountFriend.getCreationDate());
        params.addValue("confirmation_date", accountFriend.getConfirmationDate());
        params.addValue("status", accountFriend.getStatus().name());

        String sql = "insert into SocialNetwork.friends (sender_person_id, receiver_person_id, creation_date, status) " +
                "values (:sender_person_id, :receiver_person_id, :creation_date, :status)";
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteFriend(Long userId, Long userId2) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("userId1", userId);
        params.addValue("userId2", userId2);

        String sql = "delete from SocialNetwork.friends "
                + " where (sender_person_id = :userId1 and receiver_person_id = :userId2) or (sender_person_id = :userId2 and receiver_person_id = :userId1)";

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateFriend(AccountFriend accountFriend) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("sender_person_id", accountFriend.getSenderPersonId());
        params.addValue("receiver_person_id", accountFriend.getReceiverPersonId());
        params.addValue("confirmation_date", accountFriend.getConfirmationDate());
        params.addValue("status", accountFriend.getStatus().name());

        String sql = "update SocialNetwork.friends set " +
                "sender_person_id = :sender_person_id, " +
                "receiver_person_id = :receiver_person_id, " +
                "confirmation_date = :confirmation_date, " +
                "status = :status " +
                "where sender_person_id = :sender_person_id and receiver_person_id = :receiver_person_id";
        jdbcTemplate.update(sql, params);
    }

    @Override
    public List<AccountFriend> getFriends(Long userId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        params.put("status", FriendStatus.FRIEND.name());
        String sql = "select sender_person_id, receiver_person_id, creation_date, confirmation_date, status from SocialNetwork.friends" +
                " where status = :status and (sender_person_id = :userId or receiver_person_id = :userId)";
        try {
            return jdbcTemplate.query(sql, params, new FriendMapper());
        } catch (Exception e) {
            log.error("ERROR in getFriends ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<AccountFriend> getFriendRequests(Long userId, FriendRequest friendRequest) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        params.put("status", FriendStatus.REQUEST.name());

        String sql = "select sender_person_id, receiver_person_id, creation_date, confirmation_date, status from SocialNetwork.friends" +
                " where status = :status and ";
        if(friendRequest == FriendRequest.INCOMING) {
          sql+="receiver_person_id = :userId";
        } else if(friendRequest == FriendRequest.OUTGOING) {
            sql+="sender_person_id = :userId";
        }
        try {
            return jdbcTemplate.query(sql, params, new FriendMapper());
        } catch (Exception e) {
            log.error("ERROR in getFriends ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public AccountFriend getFriendStatus(Long userId1, Long userId2) throws FriendStatusNotFoundException {
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId1", userId1);
        params.put("userId2", userId2);
        String sql = "select sender_person_id, receiver_person_id, creation_date, confirmation_date, status from SocialNetwork.friends" +
                " where (sender_person_id = :userId1 and receiver_person_id = :userId2) or (sender_person_id = :userId2 and receiver_person_id = :userId1)";
        try {
            return jdbcTemplate.queryForObject(sql, params, new FriendMapper());
        } catch (EmptyResultDataAccessException e) {
            log.warn("friend result is empty");
            throw new FriendStatusNotFoundException(e);
        } catch (Exception e) {
            log.error("ERROR in getFriends ", e);
            throw new FriendStatusNotFoundException(e);
        }
    }


    private static class FriendMapper implements RowMapper<AccountFriend> {
        @Override
        public AccountFriend mapRow(ResultSet resultSet, int i) throws SQLException {
            AccountFriend friend = new AccountFriend();
            friend.setSenderPersonId(resultSet.getLong("sender_person_id"));
            friend.setReceiverPersonId(resultSet.getLong("receiver_person_id"));
            friend.setStatus(FriendStatus.valueOf(resultSet.getString("status")));
            friend.setCreationDate(resultSet.getTimestamp("creation_date"));
            friend.setConfirmationDate(resultSet.getTimestamp("confirmation_date"));
            return friend;
        }
    }
}
