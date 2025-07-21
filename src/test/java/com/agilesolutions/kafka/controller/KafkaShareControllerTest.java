package com.agilesolutions.kafka.controller;

import com.agilesolutions.config.ApplicationProperties;
import com.agilesolutions.config.JunitConfig;
import com.agilesolutions.controller.StockController;
import com.agilesolutions.dto.StockResponse;
import com.agilesolutions.exception.CustomControllerAdvice;
import com.agilesolutions.kafka.model.Share;
import com.agilesolutions.kafka.service.KafkaShareService;
import com.agilesolutions.mvc.AvroJsonHttpMessageConverter;
import com.agilesolutions.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KafkaShareController.class)
@ContextConfiguration(classes = {KafkaShareController.class, JunitConfig.class, CustomControllerAdvice.class, AvroJsonHttpMessageConverter.class})

class KafkaShareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private KafkaShareService kafkaShareService;

    @Test
    public void givenFinancialAssets_whenInquiringApple_thenReturnStockPricesForApple() throws Exception {

        // WHEN
        Mockito.when(kafkaShareService.getAllShares()).thenReturn(List.of(Share.newBuilder().setCompany("APPL").setId(1).setQuantity(10).build()));

        // THEN
        ResultActions resultActions = mockMvc.perform(get("/kafka/shares").accept("application/avro+json"))
                .andExpect(status().isOk())
                //.andDo(print());
                .andExpect(content().string(containsStringIgnoringCase("1.0")));
    }


}