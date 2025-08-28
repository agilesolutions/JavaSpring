package com.agilesolutions.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.agilesolutions.security.SecurityCustomizers.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurity {

    private static final String[] WHITLIST = {"/healthCheck","status"};


    @Order(1)
    @Bean
    public SecurityFilterChain publicEndpoints(HttpSecurity http) throws Exception {

        http.securityMatcher(WHITLIST)
                .csrf(csrfCustomizer)
                .cors(corsCustomizer)
                .headers(headersCustomizer)
                .authorizeHttpRequests(requests -> requests.anyRequest().permitAll());

        return http.build();


    }

    @Order(1)
    @Bean
    public SecurityFilterChain securedEndpoints(HttpSecurity http) throws Exception {

        // https://stackoverflow.com/questions/70949390/spring-authorization-and-resource-on-same-server
        // https://docs.spring.io/spring-authorization-server/reference/getting-started.html
        http.securityMatcher("/**")
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()).oauth2ResourceServer(rs -> rs.jwt(Customizer.withDefaults()))
                .headers(headersCustomizer)
                .csrf(csrfCustomizer)
                .cors(corsCustomizer);

        return http.build();

    }


}
