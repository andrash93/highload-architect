package ru.otus.architect.service;

import ru.otus.architect.data.FriendRequest;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.AccountFriend;

import java.util.List;

public interface FriendService {

    AccountFriend createFriend(Long accountId, Long friendId);

    AccountFriend deleteFriend(Long accountId, Long friendId);

    AccountFriend confirmFriend(Long accountId, Long friendId);

    List<Account> getFriends(Long userId);

    List<Account> getFriendRequests(Long userId, FriendRequest friendRequest);

    AccountFriend getFriendStatus(Long userId, Long userId2);
}
