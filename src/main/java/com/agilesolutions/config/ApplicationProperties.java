package com.agilesolutions.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twelvedata.api")
@Data
public class ApplicationProperties {

    private String key;

    private String url;
}