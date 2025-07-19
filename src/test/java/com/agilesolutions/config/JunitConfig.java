package com.agilesolutions.config;

import com.agilesolutions.service.StockService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JunitConfig {

    @Bean
    @Primary
    public StockService stockService() {
        return Mockito.mock(StockService.class);
    }

}
