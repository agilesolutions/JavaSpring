package com.agilesolutions.service;

import com.agilesolutions.config.ApplicationProperties;
import com.agilesolutions.dto.StockDto;
import com.agilesolutions.jpa.model.DailyStockData;
import com.agilesolutions.model.StockData;
import com.agilesolutions.rest.StockClient;
import io.micrometer.core.annotation.Counted;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.ResourceAccessException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

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
            retryFor = {
                    ResourceAccessException.class,
                    ConnectException.class,
                    SocketTimeoutException.class
            },
            maxAttemptsExpression = "${service.retry.max-attempts}",
            backoff = @Backoff(
                    delayExpression = "${service.retry.initial-delay}",
                    maxDelayExpression = "${service.retry.max-delay}",
                    multiplierExpression = "${service.retry.multiplier}"
            )
    )

    @PreAuthorize("hasRole('ADMIN')")
    @Counted(value = "stockPrices.service.invocations", description = "Number of times TwelveData.com the service is invoked")
    public StockDto getLatestStockPrices(@PathVariable String company) {

        log.info("Get stock prices for: {}", company);
        StockData data = stockClient.getLatestStockPrices(company, MINUTE_INTERVAL, 1, applicationProperties.getKey());
        DailyStockData latestData = data.getValues().get(0);
        log.info("Get stock prices ({}) -> {}", company, latestData.getClose());
        return new StockDto(Float.parseFloat(latestData.getClose()));

    }

    @Recover
    public String fallbackMethod(Exception exception, String url, Object request) {
        log.error("All retry attempts failed for URL: {}", url, exception);
        return "Stock price lookup failed after retries. Try later again...";
    }


}
