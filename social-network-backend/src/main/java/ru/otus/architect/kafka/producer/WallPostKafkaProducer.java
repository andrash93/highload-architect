package ru.otus.architect.kafka.producer;

import ru.otus.architect.data.model.WallPost;

public interface WallPostKafkaProducer {

    void sendMessage(WallPost post);

}
