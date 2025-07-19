package com.agilesolutions.actuator;

import org.bson.Document;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

public class CustomMongoHealthIndicator extends MongoHealthIndicator {

    private final MongoTemplate mongoTemplate;

    public CustomMongoHealthIndicator(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
        Assert.notNull(mongoTemplate, "MongoTemplate must not be null");
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Document result = this.mongoTemplate.executeCommand("{ isMaster: 1 }");
        builder.up().withDetail("maxWireVersion", result.getInteger("maxWireVersion"));
    }
}