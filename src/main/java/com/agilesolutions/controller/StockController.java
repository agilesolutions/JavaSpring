package com.agilesolutions.controller;

import com.agilesolutions.dto.StockResponse;
import com.agilesolutions.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/assets")
public class StockController {

    private final StockService stockService;

    @GetMapping(value = "/stockPrices/{company}", produces = MediaType.APPLICATION_JSON_VALUE)
    StockResponse getLatestStockPrices(@PathVariable("company") String company) {

        log.info("Get stock prices for: {}", company);
        return stockService.getLatestStockPrices(company);
    }


}