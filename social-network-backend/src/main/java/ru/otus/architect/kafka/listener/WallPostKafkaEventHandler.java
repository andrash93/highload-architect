package ru.otus.architect.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.service.WallPostService;

@Service
@Slf4j
public class WallPostKafkaEventHandler {

    private final WallPostService wallPostService;

    public WallPostKafkaEventHandler(WallPostService wallPostService) {
        this.wallPostService = wallPostService;
    }

    @KafkaListener(topics = "wall-post", containerFactory = "wallPostKafkaListenerContainerFactory", groupId = "wallPostConsumers")
    public void wallPostMessageListener(@Payload WallPost wallPost, @Headers MessageHeaders headers) {
        log.info("consumer {}  {}", headers, wallPost);
        wallPostService.addWallPostToFollowersCache(wallPost);
     }

}
