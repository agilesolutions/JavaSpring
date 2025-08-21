package com.agilesolutions.kafka.base;

import com.agilesolutions.config.KafkaConfig;
import com.agilesolutions.kafka.service.KafkaShareService;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.KafkaContainer;

@SpringJUnitConfig(classes = {KafkaShareService.class, KafkaConfig.class}, initializers = {ConfigDataApplicationContextInitializer.class})
public class BaseKafkaIntegrationTest {

    @ServiceConnection
    public static KafkaContainer  kafkaContainer = new KafkaContainer("confluentinc/cp-kafka:7.7.1");

    static {
        kafkaContainer.start();
    }

}