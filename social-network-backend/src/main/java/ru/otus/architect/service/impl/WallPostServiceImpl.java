package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.architect.cache.Cache;
import ru.otus.architect.cache.redis.WallPostCache;
import ru.otus.architect.dao.WallPostDao;
import ru.otus.architect.data.model.Account;
import ru.otus.architect.data.model.Follow;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.kafka.producer.WallPostKafkaProducer;
import ru.otus.architect.service.AccountService;
import ru.otus.architect.service.FollowService;
import ru.otus.architect.service.WallPostService;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
public class WallPostServiceImpl implements WallPostService {

    private final WallPostDao wallPostDao;
    private final Cache<String, WallPost> wallPostCache;
    private final WallPostKafkaProducer wallPostKafkaProducer;
    private final AccountService accountService;
    private final FollowService followService;

    public WallPostServiceImpl(WallPostDao wallPostDao,
                               WallPostCache wallPostCache,
                               WallPostKafkaProducer wallPostKafkaProducer,
                               AccountService accountService,
                               FollowService followService) {
        this.wallPostDao = wallPostDao;
        this.wallPostCache = wallPostCache;
        this.wallPostKafkaProducer = wallPostKafkaProducer;
        this.accountService = accountService;
        this.followService = followService;
    }

    @Override
    public WallPost addWallPost(Long userId, WallPost post) {
        Account account = accountService.getAccountById(userId);
        post.setUserId(userId);
        post.setFullUserName(account.getName() + " " + account.getSurname());
        post.setDateCreated(new Timestamp(System.currentTimeMillis()));
        WallPost wallPost = wallPostDao.addWallPost(post);
        wallPostKafkaProducer.sendMessage(wallPost);
        return wallPost;
    }

    @Override
    public List<WallPost> getUserSubscribeWallPost(Long userId) {
        log.info("userId {} invoke getUserWallPost", userId);
        List<WallPost> posts = wallPostCache.get(String.valueOf(userId));
        if (CollectionUtils.isEmpty(posts)) {
            log.info("userId {} getUserWallPost in database", userId);
            posts = wallPostDao.getWallPosts(userId);
            wallPostCache.putAll(String.valueOf(userId), posts);
        }
        return posts;
    }

    @Override
    public void addWallPostToFollowersCache(WallPost post) {
        List<Follow> followers = followService.getFollowers(post.getUserId());
        for (Follow follow : followers) {
            log.info("userId {} update wall post cache", follow.getFollowerId());
            putWallPostToCache(follow.getFollowerId(), post);
        }
    }

    private void putWallPostToCache(Long userId, WallPost post) {
        if (wallPostCache.size(String.valueOf(userId)) == 0L) { //на случай если что то случилось с кешом и он вдруг пропал пропал
            log.info("userId {} getUserWallPost for cache in database", userId);
            List<WallPost> wallPosts = wallPostDao.getWallPosts(userId);
            wallPostCache.putAll(String.valueOf(userId), wallPosts);
        } else {
            wallPostCache.put(String.valueOf(userId), post);
        }
    }
}
