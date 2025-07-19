package com.agilesolutions.kafka.service;

import com.agilesolutions.kafka.model.Share;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaShareService {

    @Autowired
    private final Consumer<String, Share> consumer;



    public List<Share> getAllShares() {

        AtomicBoolean continueProcessing = new AtomicBoolean(true);

        List<Share> shares = new ArrayList<>();

        log.info("Get all Shares");

        final int giveUp = 100;   int noRecordsCount = 0;

        while (continueProcessing.get()) {
            //polling the records since the last offset.
            final ConsumerRecords<String, Share> consumerRecords =
                    consumer.poll(1000);

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }
            //Printing the messages received in a for loop
            consumerRecords.forEach(record -> {
                shares.add(record.value());
                continueProcessing.set(false);
            });
            //committing the offset of messages in Async mode
            consumer.commitAsync();
        }
        //consumer.close();
        return shares;

    }

}
