package com.agilesolutions.service;

import com.agilesolutions.config.ApplicationProperties;
import com.agilesolutions.dto.StockResponse;
import com.agilesolutions.jpa.model.DailyStockData;
import com.agilesolutions.model.StockData;
import com.agilesolutions.rest.StockClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = {StockService.class, ApplicationProperties.class}, initializers = {ConfigDataApplicationContextInitializer.class})
@TestPropertySource(properties = { "spring.config.location=classpath:application.yaml" })
@EnableConfigurationProperties(value = ApplicationProperties.class)

class StockServiceTest {


    private static final String MINUTE_INTERVAL = "1min";

    @MockitoBean
    StockClient stockClient;

    @Autowired
    StockService stockService;

    @Autowired
    ApplicationProperties applicationProperties;

    @Test
    public void givenAvailable_whenRetrievingStocks_thenReturnPrices() {

        // WHEN
        Mockito.when(stockClient.getLatestStockPrices("AAPL", MINUTE_INTERVAL, 1, applicationProperties.getKey()))
                .thenReturn(StockData.builder().values(List.of(DailyStockData.builder().close("1.0").build())).build());


        StockResponse response = stockService.getLatestStockPrices("AAPL");

        assertAll("verify result",
                () -> assertTrue(response.price() > 0)
        );


    }

}