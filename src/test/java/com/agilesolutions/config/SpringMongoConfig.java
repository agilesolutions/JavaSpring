package com.agilesolutions.config;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class SpringMongoConfig {

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    @Bean
    public MongoTemplate mongoTemplate()  {
        String ip = "localhost";
        int port = 27017;
        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)),"test");
    }

}
