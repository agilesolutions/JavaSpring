package com.agilesolutions.kafka.service;

import com.agilesolutions.kafka.base.BaseKafkaIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

class KafkaShareIntegrationServiceTest extends BaseKafkaIntegrationTest {

    @Autowired
    private KafkaShareService kafkaShareService;

    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException {

        com.agilesolutions.kafka.model.Share share = com.agilesolutions.kafka.model.Share.newBuilder().setCompany("AAPL").setId(1).setQuantity(100).build();

        kafkaShareService.sendShare(share);

    }

    @Test
    void getAllShares() {

        var shares = kafkaShareService.getAllShares();
        assert shares != null;
        assert !shares.isEmpty();
        assert "AAPL".equals(shares.get(0).getCompany());

    }


}