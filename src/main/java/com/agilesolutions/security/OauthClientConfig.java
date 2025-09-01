package com.agilesolutions.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.OAuth2ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

/**
 * RestClient Support for OAuth2 in Spring Security 6.4
 * Read https://spring.io/blog/2024/10/28/restclient-support-for-oauth2-in-spring-security-6-4
 *
 */
@Configuration
@Slf4j
public class OauthClientConfig {


    @Bean
    public RestClient restClient(RestClient.Builder builder, OAuth2AuthorizedClientManager authorizedClientManager) {
        OAuth2ClientHttpRequestInterceptor requestInterceptor =
                new OAuth2ClientHttpRequestInterceptor(authorizedClientManager);

        return builder.requestInterceptor(requestInterceptor).build();
    }

}