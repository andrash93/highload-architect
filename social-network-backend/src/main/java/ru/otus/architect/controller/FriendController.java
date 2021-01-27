package ru.otus.architect.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.architect.controller.utils.AccountControllerUtils;
import ru.otus.architect.data.dto.account.AccountInfoResponse;
import ru.otus.architect.data.dto.friend.FriendRequest;
import ru.otus.architect.data.dto.friend.FriendResponse;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.AccountFriend;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.service.FriendService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @PostMapping("/friend/add")
    @ResponseStatus(HttpStatus.OK)
    public FriendResponse addFriend(@RequestBody FriendRequest friendRequest, Auth auth) {
        log.info("currentAccountId {} invoke addFriend {}", auth.getUserId(), friendRequest.getFriendId());
        AccountFriend friend = friendService.createFriend(auth.getUserId(), friendRequest.getFriendId());
        return new FriendResponse(friend);
    }

    @PostMapping("/friend/cancel")
    @ResponseStatus(HttpStatus.OK)
    public FriendResponse cancelFriend(@RequestBody FriendRequest friendRequest, Auth auth) {
        log.info("currentAccountId {} invoke cancelFriend {}", auth.getUserId(), friendRequest.getFriendId());
        AccountFriend accountFriend = friendService.deleteFriend(auth.getUserId(), friendRequest.getFriendId());
        return new FriendResponse(accountFriend);
    }

    @PostMapping("/friend/decline")
    @ResponseStatus(HttpStatus.OK)
    public FriendResponse declineFriend(@RequestBody FriendRequest friendRequest, Auth auth) {
        log.info("currentAccountId {} invoke cancelFriend {}", auth.getUserId(), friendRequest.getFriendId());
        AccountFriend accountFriend = friendService.deleteFriend(auth.getUserId(), friendRequest.getFriendId());
        return new FriendResponse(accountFriend);
    }

    @PostMapping("/friend/delete")
    @ResponseStatus(HttpStatus.OK)
    public FriendResponse deleteFriend(@RequestBody FriendRequest friendRequest, Auth auth) {
        log.info("currentAccountId {} invoke deleteFriend {}", auth.getUserId(), friendRequest.getFriendId());
        AccountFriend accountFriend = friendService.deleteFriend(auth.getUserId(), friendRequest.getFriendId());
        return new FriendResponse(accountFriend);
    }

    @PostMapping("/friend/confirm")
    @ResponseStatus(HttpStatus.OK)
    public FriendResponse confirmFriend(@RequestBody FriendRequest friendRequest, Auth auth) {
        log.info("currentAccountId {} invoke confirmFriend {}", auth.getUserId(), friendRequest.getFriendId());
        AccountFriend accountFriend = friendService.confirmFriend(auth.getUserId(), friendRequest.getFriendId());
        return new FriendResponse(accountFriend);
    }

    @GetMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountInfoResponse> getAllFriends(Auth auth) {
        log.info("currentAccountId {} invoke getAllFriends", auth.getUserId());
        List<Account> friends = friendService.getFriends(auth.getUserId());
        List<AccountInfoResponse> response = new ArrayList<>();
        friends.forEach(account -> response.add(AccountControllerUtils.getAccountInfoResponse(account)));
        return response;
    }

    @GetMapping("/friends/incoming")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountInfoResponse> getIncomingFriendsRequest(Auth auth) {
        log.info("currentAccountId {} invoke getIncomingFriendsRequest", auth.getUserId());
        List<Account> friends = friendService.getFriendRequests(auth.getUserId(), ru.otus.architect.data.FriendRequest.INCOMING);
        List<AccountInfoResponse> response = new ArrayList<>();
        friends.forEach(account -> response.add(AccountControllerUtils.getAccountInfoResponse(account)));
        return response;
    }

    @GetMapping("/friends/outgoing")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountInfoResponse> getOutgoingFriendsRequest(Auth auth) {
        log.info("currentAccountId {} invoke getOutgoingFriendsRequest", auth.getUserId());
        List<Account> friends = friendService.getFriendRequests(auth.getUserId(), ru.otus.architect.data.FriendRequest.OUTGOING);
        List<AccountInfoResponse> response = new ArrayList<>();
        friends.forEach(account -> response.add(AccountControllerUtils.getAccountInfoResponse(account)));
        return response;
    }

}
