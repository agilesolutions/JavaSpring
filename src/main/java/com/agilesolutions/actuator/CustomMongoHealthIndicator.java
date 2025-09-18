package com.agilesolutions.actuator;

import org.bson.Document;
import org.springframework.boot.actuate.data.mongo.MongoHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

/**
 * Custom health indicator for MongoDB that extends {@link MongoHealthIndicator}.
 * <p>
 * This indicator executes the MongoDB {@code isMaster} command to determine the health of the database,
 * and adds the {@code maxWireVersion} detail to the health status.
 * </p>
 */
public class CustomMongoHealthIndicator extends MongoHealthIndicator {

    private final MongoTemplate mongoTemplate;

    /**
     * Constructs a new {@code CustomMongoHealthIndicator} with the given {@link MongoTemplate}.
     *
     * @param mongoTemplate the MongoTemplate to use for health checks; must not be {@code null}
     * @throws IllegalArgumentException if {@code mongoTemplate} is {@code null}
     */
    public CustomMongoHealthIndicator(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
        Assert.notNull(mongoTemplate, "MongoTemplate must not be null");
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Performs a health check by executing the {@code isMaster} command on the MongoDB instance.
     * Adds the {@code maxWireVersion} detail to the health status.
     *
     * @param builder the health builder to report status and details
     * @throws Exception if the health check fails
     */
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Document result = this.mongoTemplate.executeCommand("{ isMaster: 1 }");
        builder.up().withDetail("maxWireVersion", result.getInteger("maxWireVersion"));
    }
}