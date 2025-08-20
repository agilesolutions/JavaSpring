package com.agilesolutions.service;

import com.agilesolutions.config.ApplicationProperties;
import com.agilesolutions.dto.StockResponse;
import com.agilesolutions.jpa.model.DailyStockData;
import com.agilesolutions.model.StockData;
import com.agilesolutions.rest.StockClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Slf4j
@AllArgsConstructor
public class StockService {

    @Qualifier("stockClient")
    private final StockClient stockClient;

    private final ApplicationProperties applicationProperties;

    private static final String MINUTE_INTERVAL = "1min";
    private static final String DAY_INTERVAL = "1day";

    @Retryable(
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2) // start with 2s, then 4s, then 8s
    )
    public StockResponse getLatestStockPrices(@PathVariable String company) {

        log.info("Get stock prices for: {}", company);
        StockData data = stockClient.getLatestStockPrices(company, MINUTE_INTERVAL, 1, applicationProperties.getKey());
        DailyStockData latestData = data.getValues().get(0);
        log.info("Get stock prices ({}) -> {}", company, latestData.getClose());
        return new StockResponse(Float.parseFloat(latestData.getClose()));

    }

}
