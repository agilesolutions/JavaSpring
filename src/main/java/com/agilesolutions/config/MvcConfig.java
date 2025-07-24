package com.agilesolutions.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebMvc
@Slf4j
public class MvcConfig  implements WebMvcConfigurer {


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("Message converters : {}", converters.stream().map(mc -> mc.getSupportedMediaTypes()).flatMap(l -> l.stream()).map(m -> m.toString()).collect(Collectors.joining(",")));
    }

}