package ru.otus.architect.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.cache.redis.WallPostCache;
import ru.otus.architect.service.WallPostService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    private final WallPostService wallPostService;
    private final WallPostCache wallPostCache;

    public TestController(WallPostService wallPostService, WallPostCache wallPostCache) {
        this.wallPostService = wallPostService;
        this.wallPostCache = wallPostCache;
    }

    @GetMapping("/success")
    @Timed(value = "social.network.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public String test() {
        log.info("invoke test");
        return "test success response";
    }

    @GetMapping("/wall/add")
    @Timed(value = "social.network.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public WallPost wallPost(@RequestBody WallPost post) {
        log.info("invoke wallPost");
        return wallPostService.addWallPost(1L, post);
    }

    @GetMapping("/wall/get/{userId}")
    @Timed(value = "social.network.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public List<WallPost> wallPost(@PathVariable Long userId) {
        log.info("invoke wallPost");
        return wallPostService.getUserSubscribeWallPost(userId);
    }

    @GetMapping("/s/add")
    @Timed(value = "social.network.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public void wallPosts(HttpServletRequest request, @RequestBody WallPost post) {
        log.info("invoke wallPost");
        wallPostCache.put(String.valueOf(post.getUserId()), post);
    }

    @GetMapping("/s/get/{userId}")
    @Timed(value = "social.network.request", histogram = true, percentiles = {0.5, 0.95, 0.99, 1})
    public List<WallPost> wallPosts(@PathVariable String userId) {
        log.info("invoke wallPost");
        return wallPostCache.get(String.valueOf(userId));
    }

}
