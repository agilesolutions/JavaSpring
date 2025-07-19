package com.agilesolutions.init;


import com.agilesolutions.kafka.model.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class KafkaInitializer implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, Share> kafkaTemplate;



    @Override
    public void run(String... args) throws ExecutionException, InterruptedException {

        Share share = Share.newBuilder().setCompany("AAPL").setId(1).setQuantity(100).build();

        CompletableFuture<SendResult<String, Share>> future = kafkaTemplate.send("default", null, share);

        SendResult metadata = future.get();

        future.whenComplete((result, ex) -> {

            if (ex != null) {
                log.error("Unable to send Share=["
                        + share.toString() + "] due to : " + ex.getMessage());
            } else {
                log.info("Sent message=[" + share.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]" + "partition=[" + result.getRecordMetadata().partition() + "]");
            }
        });

        log.info("Kafka records published successfully-------");

    }
}
