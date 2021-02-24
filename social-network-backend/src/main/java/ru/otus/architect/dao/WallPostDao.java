package ru.otus.architect.dao;

import ru.otus.architect.data.model.WallPost;

import java.util.List;

public interface WallPostDao {

    WallPost addWallPost(WallPost post);

    List<WallPost> getWallPosts(Long userId);

}
