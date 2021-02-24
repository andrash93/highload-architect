package ru.otus.architect.dao;

import ru.otus.architect.data.model.Follow;

import java.util.List;

public interface FollowDao {

    void addFollow(Follow follow);

    void deleteFollow(Follow follow);

    List<Follow> getFollowers(Long userId);

}
