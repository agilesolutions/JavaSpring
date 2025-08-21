package com.agilesolutions.kafka.controller;

import com.agilesolutions.config.JunitConfig;
import com.agilesolutions.config.MvcConfig;
import com.agilesolutions.exception.CustomControllerAdvice;
import com.agilesolutions.kafka.model.Share;
import com.agilesolutions.kafka.service.KafkaShareService;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KafkaShareController.class)
@ContextConfiguration(classes = {KafkaShareController.class, JunitConfig.class, MvcConfig.class, CustomControllerAdvice.class})
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
        ResultActions resultActions = mockMvc.perform(get("/kafka/shares").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andDo(print());
                .andExpect(content().string(containsStringIgnoringCase("10")));
    }


    @Test
    public void givenInvalidRequest_whenIllegalArgumentExceptionThrown_thenReturnBadRequest() throws Exception {
        // WHEN
        Mockito.when(kafkaShareService.getAllShares()).thenThrow(new NullPointerException("action is null"));

        // THEN
        mockMvc.perform(get("/kafka/shares").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("action is null")));
    }

    @Test
    public void givenUnexpectedError_whenExceptionThrown_thenReturnInternalServerError() throws Exception {
        // WHEN
        Mockito.when(kafkaShareService.getAllShares()).thenThrow(new RuntimeException("Unexpected error"));

        // THEN
        mockMvc.perform(get("/kafka/shares").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Unexpected error")));
    }

}