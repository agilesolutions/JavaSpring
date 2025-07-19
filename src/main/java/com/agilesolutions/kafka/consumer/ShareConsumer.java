package com.agilesolutions.kafka.consumer;

import com.agilesolutions.kafka.model.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShareConsumer {

    //@KafkaListener(topics = "default", containerFactory = "kafkaListenerContainerFactory", groupId = "default")
    public void listen(Share share) {
        log.info("Starting consuming from transaction_events_topic - {}", share.toString());
    }
}