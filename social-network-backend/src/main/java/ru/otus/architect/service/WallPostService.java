package ru.otus.architect.service;

import ru.otus.architect.data.model.WallPost;

import java.util.List;

public interface WallPostService {

    WallPost addWallPost(Long userId, WallPost post);

    void addWallPostToFollowersCache(WallPost post);

    List<WallPost> getUserSubscribeWallPost(Long userId);
}
