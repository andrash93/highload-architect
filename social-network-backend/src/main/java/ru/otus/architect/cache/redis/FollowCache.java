package ru.otus.architect.cache.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.otus.architect.cache.Cache;
import ru.otus.architect.data.model.Follow;

import java.util.Collections;
import java.util.List;

@Service
public class FollowCache implements Cache<String, Follow> {

    private final ListOperations<String, Follow> listOps;

    public FollowCache(RedisTemplate redisTemplate) {
        this.listOps = redisTemplate.opsForList();
    }

    @Override
    public List<Follow> get(String key) {
        try {
            return listOps.range(key(key), 0L, listOps.size(key(key)));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void put(String key, Follow val) {
        listOps.leftPush(key(key), val);
    }

    @Override
    public void putAll(String key, List<Follow> val) {
        listOps.leftPushAll(key(key), val);
    }

    @Override
    public long size(String key) {
        return listOps.size(key(key));
    }

    private String key(String key) {
        return "follow:" + key;
    }
}
