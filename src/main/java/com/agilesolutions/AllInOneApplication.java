package com.agilesolutions;

import com.agilesolutions.mongo.repository.MongoDBShareRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class AllInOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(AllInOneApplication.class, args);
    }
}
