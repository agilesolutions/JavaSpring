package com.agilesolutions.config;

import com.agilesolutions.kafka.model.Share;
import com.agilesolutions.kafka.service.KafkaShareService;
import com.agilesolutions.service.StockService;
import org.apache.kafka.clients.consumer.Consumer;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class JunitConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

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
    public Consumer<String, Share> consumer() {return Mockito.mock(Consumer.class);}


}
