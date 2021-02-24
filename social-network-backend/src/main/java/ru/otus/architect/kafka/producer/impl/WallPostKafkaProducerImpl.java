package ru.otus.architect.kafka.producer.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import ru.otus.architect.data.model.WallPost;
import ru.otus.architect.kafka.producer.WallPostKafkaProducer;


@Service
@Slf4j
public class WallPostKafkaProducerImpl implements WallPostKafkaProducer {

    private final String WALL_POST_TOPIC = "wall-post";

    private final KafkaTemplate<String, WallPost> kafkaTemplate;

    public WallPostKafkaProducerImpl(KafkaTemplate<String, WallPost> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(WallPost post) {
        ListenableFuture<SendResult<String, WallPost>> future =
                kafkaTemplate.send(WALL_POST_TOPIC, String.valueOf(post.getUserId()), post);
        future.addCallback(
                stringWallPostSendResult ->
                        log.info("send post success. topic {}, partition {} key {}", stringWallPostSendResult.getProducerRecord().topic(),
                                stringWallPostSendResult.getProducerRecord().partition(), stringWallPostSendResult.getProducerRecord().key()),

                throwable -> log.error("error send post ", throwable)
        );
    }
}
