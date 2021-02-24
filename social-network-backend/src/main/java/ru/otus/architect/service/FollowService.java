package ru.otus.architect.service;

import ru.otus.architect.data.model.Follow;

import java.util.List;

public interface FollowService {

    void addFollow(Follow follow);

    void deleteFollow(Follow follow);

    List<Follow> getFollowers(Long userId);
}
