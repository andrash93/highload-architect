package ru.otus.architect.cache.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;
import ru.otus.architect.cache.Cache;
import ru.otus.architect.data.model.WallPost;

import java.util.List;

@Service
public class WallPostCache implements Cache<String, WallPost> {

    private final int cacheMaxSize = 1000;
    private final ListOperations<String, WallPost> listOps;

    public WallPostCache(RedisTemplate redisTemplate) {
        this.listOps = redisTemplate.opsForList();
    }

    @Override
    public List<WallPost> get(String key) {
        return listOps.range(key(key), 0L, cacheMaxSize);
    }

    @Override
    public void put(String key, WallPost val) {
        if (listOps.size(key(key)) > cacheMaxSize) {
            listOps.rightPop(key(key));
        }
        listOps.leftPush(key(key), val);
    }

    @Override
    public void putAll(String key, List<WallPost> val) {
        listOps.leftPushAll(key(key), val);
    }

    @Override
    public long size(String key) {
        return listOps.size(key(key));
    }

    private String key(String key) {
        return "wall:" + key;
    }
}
