package com.agilesolutions.mongo.base;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

public class BaseIntegrationTest {

    @ServiceConnection
    protected static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

    static {
        mongoDBContainer.start();
    }

}