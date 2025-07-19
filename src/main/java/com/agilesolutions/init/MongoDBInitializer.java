package com.agilesolutions.init;


import com.agilesolutions.mongo.model.Share;
import com.agilesolutions.mongo.repository.MongoDBShareRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class MongoDBInitializer implements CommandLineRunner {

    @Autowired
    private MongoDBShareRepository shareRepository;



    @Override
    public void run(String... args) {

        shareRepository.saveAll(List.of(
                Share.builder().id(1L).company("AAPL").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(2L).company("AMZN").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(3L).company("META").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(4L).company("MSFT").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(5L).company("NVDA").quantity(100).updated(LocalDate.now()).build()
        ));

        log.info("MongoDB records saved successfully-------");

    }
}
