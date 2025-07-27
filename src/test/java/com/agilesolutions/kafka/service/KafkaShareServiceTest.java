package com.agilesolutions.kafka.service;

import com.agilesolutions.kafka.model.Share;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class KafkaShareServiceTest {

    @Mock
    private Consumer<String, Share> consumer;

    private KafkaShareService kafkaShareService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaShareService = new KafkaShareService(consumer);
    }

    @Test
    void getAllSharesReturnsEmptyListWhenNoRecords() {
        when(consumer.poll(anyLong())).thenReturn(ConsumerRecords.empty());
        List<Share> shares = kafkaShareService.getAllShares();
        assertEquals(0, shares.size());
    }

    @Test
    void getAllSharesStopsPollingAfterMaxAttempts() {
        when(consumer.poll(anyLong())).thenReturn(ConsumerRecords.empty());
        kafkaShareService.getAllShares();
        verify(consumer, atLeast(100)).poll(anyLong());
    }
}