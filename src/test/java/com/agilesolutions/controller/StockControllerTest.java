package com.agilesolutions.controller;

import com.agilesolutions.dto.StockResponse;
import com.agilesolutions.exception.CustomControllerAdvice;
import com.agilesolutions.service.StockService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = StockController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {StockController.class, CustomControllerAdvice.class})
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StockService stockService;

    @Test
    public void givenFinancialAssets_whenInquiringApple_thenReturnStockPricesForApple() throws Exception {

        // WHEN
        Mockito.when(stockService.getLatestStockPrices("APPL")).thenReturn(StockResponse.builder().price(1.0F).build());

        // THEN
        ResultActions resultActions = mockMvc.perform(get("/api/assets/stockPrices/{company}", "APPL"))
                .andExpect(status().isOk())
                //.andDo(print());
                .andExpect(content().string(containsStringIgnoringCase("1.0")));
    }

    @Test
    public void givenResourceNotAvailable_whenGetSpecificException_thenTimeOut() throws Exception {

        // WHEN
        Mockito.when(stockService.getLatestStockPrices("NONE")).thenThrow(new IllegalStateException("Resource not Available"));


        mockMvc.perform(get("/api/assets/stockPrices/{company}", "NONE"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalStateException))
                .andExpect(result -> assertEquals("Resource not Available", result.getResolvedException().getMessage()));
    }
}