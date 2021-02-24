package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.architect.dao.FriendDao;
import ru.otus.architect.data.FriendRequest;
import ru.otus.architect.data.FriendStatus;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.AccountFriend;
import ru.otus.architect.data.model.Follow;
import ru.otus.architect.exception.friend.FriendStatusNotFoundException;
import ru.otus.architect.service.AccountService;
import ru.otus.architect.service.FollowService;
import ru.otus.architect.service.FriendService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {

    private final FriendDao friendDao;
    private final AccountService accountService;
    private final FollowService followService;

    public FriendServiceImpl(FriendDao friendDao,
                             AccountService accountService,
                             FollowService followService) {
        this.friendDao = friendDao;
        this.accountService = accountService;
        this.followService = followService;
    }

    @Override
    public AccountFriend createFriend(Long accountId, Long friendId) {
        AccountFriend accountFriend = new AccountFriend(accountId,
                friendId,
                new Timestamp(System.currentTimeMillis()),
                null, FriendStatus.REQUEST);
        this.friendDao.createFriend(accountFriend);
        return accountFriend;
    }

    @Override
    public AccountFriend deleteFriend(Long accountId, Long friendId) {
        this.friendDao.deleteFriend(accountId, friendId);
        this.followService.deleteFollow(new Follow(accountId, friendId));
        return new AccountFriend(FriendStatus.NONE);
    }

    @Override
    public AccountFriend confirmFriend(Long currentAccountId, Long friendId) {
        AccountFriend accountFriend = new AccountFriend(friendId,
                currentAccountId,
                null,
                new Timestamp(System.currentTimeMillis()), FriendStatus.FRIEND);
        this.friendDao.updateFriend(accountFriend);
        this.followService.addFollow(new Follow(currentAccountId, friendId));
        this.followService.addFollow(new Follow(friendId, currentAccountId));
        return accountFriend;
    }

    @Override
    public List<Account> getFriends(Long userId) {
        List<AccountFriend> friends = this.friendDao.getFriends(userId);
        List<Long> accountIds = getFriendAccountIds(userId, friends);
        return accountService.getAccountsByIds(accountIds);
    }

    @Override
    public List<Account> getFriendRequests(Long userId, FriendRequest friendRequest) {
        List<AccountFriend> friends = this.friendDao.getFriendRequests(userId, friendRequest);
        List<Long> accountIds = getFriendAccountIds(userId, friends);
        return accountService.getAccountsByIds(accountIds);
    }

    @Override
    public AccountFriend getFriendStatus(Long userId, Long userId2) {
        try {
            return friendDao.getFriendStatus(userId, userId2);
        } catch (FriendStatusNotFoundException e) {
            log.warn("friend result is empty");
            return new AccountFriend(FriendStatus.NONE);
        }
    }

    private List<Long> getFriendAccountIds(Long currentAccountId, List<AccountFriend> friends) {
        List<Long> accountIds = new ArrayList<>();
        for (AccountFriend friend : friends) {
            if (friend.getReceiverPersonId().equals(currentAccountId)) {
                accountIds.add(friend.getSenderPersonId());
            } else {
                accountIds.add(friend.getReceiverPersonId());
            }
        }
        return accountIds;
    }

}
