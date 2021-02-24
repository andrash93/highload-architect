package ru.otus.architect.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.architect.data.dto.wall.WallPostRequest;
import ru.otus.architect.data.dto.wall.WallPostResponse;
import ru.otus.architect.data.mapper.WallPostMapper;
import ru.otus.architect.data.model.Auth;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.service.WallPostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class WallPostController {
    private final WallPostService wallPostService;

    public WallPostController(WallPostService wallPostService) {
        this.wallPostService = wallPostService;
    }

    @PostMapping("/wallpost")
    @ResponseStatus(HttpStatus.OK)
    @Timed(value = "social.network.wallpost.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public WallPostResponse addWallPost(Auth auth, @RequestBody WallPostRequest wallPostRequest) {
        log.info("userId {} invoke addWallPost {}", auth.getUserId(), wallPostRequest);
        WallPost wallPost = wallPostService.addWallPost(auth.getUserId(), WallPostMapper.mapper.wallPostRequestToWallPost(wallPostRequest));
        return WallPostMapper.mapper.wallPostToWallPostResponse(wallPost);
    }

    @GetMapping("/wallpost")
    @Timed(value = "social.network.wallpost.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public List<WallPostResponse> getWallPosts(Auth auth) {
        List<WallPostResponse> responses = new ArrayList<>();
        List<WallPost> userSubscribeWallPost = wallPostService.getUserSubscribeWallPost(auth.getUserId());
        for (WallPost post : userSubscribeWallPost) {
            responses.add(WallPostMapper.mapper.wallPostToWallPostResponse(post));
        }
        return responses;
    }

}
