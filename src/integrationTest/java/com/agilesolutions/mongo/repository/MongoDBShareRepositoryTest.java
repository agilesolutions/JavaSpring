package com.agilesolutions.mongo.repository;

import com.agilesolutions.mongo.base.BaseMongoDBIntegrationTest;
import com.agilesolutions.mongo.model.Share;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

class MongoDBShareRepositoryTest extends BaseMongoDBIntegrationTest {


    @Autowired
    private MongoDBShareRepository shareRepository;

    @BeforeEach
    void setUp() {

        shareRepository.saveAll(List.of(
                Share.builder().id(1L).company("AAPL").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(2L).company("AMZN").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(3L).company("META").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(4L).company("MSFT").quantity(100).updated(LocalDate.now()).build(),
                Share.builder().id(5L).company("NVDA").quantity(100).updated(LocalDate.now()).build()
        ));
    }

    @Test
    void findItemByCompany() {

        Share share = shareRepository.findItemByCompany("AAPL");
        assert share != null;
        assert "AAPL".equals(share.getCompany());

    }



}