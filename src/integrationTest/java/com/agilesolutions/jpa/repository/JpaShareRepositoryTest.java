package com.agilesolutions.jpa.repository;

import com.agilesolutions.jpa.base.BaseIntegrationTest;
import com.agilesolutions.jpa.model.Share;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class JpaShareRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private JpaShareRepository shareRepository;

    @BeforeEach
    void setUp() {

        shareRepository.deleteAll();

    }

    @Test
    void findByCompany() {

        shareRepository.save(new Share(null, "company1", 1, LocalDate.now()));

        shareRepository.findByCompany("company1").stream().forEach(c -> Assert.assertTrue(c.getCompany().contains("company1")));


    }

    @Test
    void findByPublishedDateAfter() {
    }
}