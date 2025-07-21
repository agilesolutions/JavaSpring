package com.agilesolutions.config;

import com.agilesolutions.kafka.service.KafkaShareService;
import com.agilesolutions.mvc.AvroJsonHttpMessageConverter;
import com.agilesolutions.service.StockService;
import org.apache.avro.specific.SpecificRecordBase;
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

    @Bean
    @Primary
    public KafkaShareService kafkaShareService() {
        return Mockito.mock(KafkaShareService.class);
    }


    @Bean
    public AvroJsonHttpMessageConverter<SpecificRecordBase> createAvroHttpMessageConverter() {

        return new AvroJsonHttpMessageConverter<>();
    }
}
