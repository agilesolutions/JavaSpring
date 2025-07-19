package com.agilesolutions.rest;

import com.agilesolutions.config.ApplicationProperties;
import com.agilesolutions.config.RestConfig;
import com.agilesolutions.model.StockData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {RestConfig.class, ApplicationProperties.class}, initializers = {ConfigDataApplicationContextInitializer.class})
@TestPropertySource(properties = { "spring.config.location=classpath:application.yaml" })
@EnableConfigurationProperties(value = ApplicationProperties.class)
class StockClientTest {

    private static final String MINUTE_INTERVAL = "1min";

    @Autowired
    StockClient stockClient;

    @Autowired
    ApplicationProperties applicationProperties;

    @ParameterizedTest
    @ValueSource(strings = {"AAPL"})
    public void givenAvailable_whenRetrievingStocks_thenReturnPrices(String company) {

        StockData data = stockClient.getLatestStockPrices(company, MINUTE_INTERVAL, 1, applicationProperties.getKey());

        assertAll("Check JSON response",
                () -> assertNotEquals(0, data.getValues().get(0).getClose()));


    }

  
}