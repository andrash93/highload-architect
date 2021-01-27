package ru.otus.architect.dao;

import ru.otus.architect.data.FriendRequest;
import ru.otus.architect.data.model.AccountFriend;
import ru.otus.architect.exception.friend.FriendStatusNotFoundException;

import java.util.List;

public interface FriendDao {

    void createFriend(AccountFriend accountFriend);

    void deleteFriend(Long userId, Long userId2);

    void updateFriend(AccountFriend accountFriend);

    List<AccountFriend> getFriends(Long userId);

    List<AccountFriend> getFriendRequests(Long userId, FriendRequest friendRequest);

    AccountFriend getFriendStatus(Long userId, Long userId2) throws FriendStatusNotFoundException;
}
