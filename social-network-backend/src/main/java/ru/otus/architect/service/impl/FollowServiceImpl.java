package ru.otus.architect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.architect.cache.Cache;
import ru.otus.architect.dao.FollowDao;
import ru.otus.architect.data.model.Follow;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.service.FollowService;

import java.util.List;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService {

    private final Cache<String, Follow> followCache;
    private final FollowDao followDao;

    public FollowServiceImpl(Cache<String, Follow> followCache,
                             FollowDao followDao) {
        this.followCache = followCache;
        this.followDao = followDao;
    }

    @Override
    public void addFollow(Follow follow) {
        followDao.addFollow(follow);
        putFollowerToCache(follow);
    }

    @Override
    public void deleteFollow(Follow follow) {
        //todo удать из кеша тоже
        followDao.deleteFollow(follow);
    }

    @Override
    public List<Follow> getFollowers(Long userId) {
        log.info("userId {} invoke getFollowers", userId);
        List<Follow> follows = followCache.get(String.valueOf(userId));
        if (CollectionUtils.isEmpty(follows)) {
            log.info("userId {} getFollowers in database", userId);
            follows = followDao.getFollowers(userId);
            followCache.putAll(String.valueOf(userId), follows);
        }
        return follows;
    }

    private void putFollowerToCache(Follow follow) {
        if (followCache.size(String.valueOf(follow.getUserId())) == 0L) { //на случай если что то случилось с кешом и он вдруг пропал пропал
            log.info("userId {} getFollowers for cache in database", follow.getUserId());
            List<Follow> followers = followDao.getFollowers(follow.getUserId());
            followCache.putAll(String.valueOf(follow.getUserId()), followers);
        } else {
            followCache.put(String.valueOf(follow.getUserId()), follow);
        }
    }
}
