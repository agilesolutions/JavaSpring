package com.agilesolutions.config;

import com.agilesolutions.mvc.AvroJsonHttpMessageConverter;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//@Configuration
//@EnableWebMvc
public class MvcConfig  implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new AvroJsonHttpMessageConverter<SpecificRecordBase>());
        converters.add(new MappingJackson2HttpMessageConverter());
    }

}