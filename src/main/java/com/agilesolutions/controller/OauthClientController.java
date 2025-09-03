package com.agilesolutions.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.dto.StockResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/oauth-client")
public class OauthClientController {

    private final RestClient restClient;


    @GetMapping("/stockPrices")
    public StockResponse stockPrices() {
        return this.restClient.get()
                .uri("http://localhost:8080/api/assets/stockPrices/AAPL")
                .accept(MediaType.APPLICATION_JSON)
                .attributes(clientRegistrationId("my-client"))
                .retrieve()
                .body(StockResponse.class);

    }

    @GetMapping("/shares")
    public ResponseEntity<List<ShareDTO>> shares() {
        List<ShareDTO> shares = this.restClient.get()
                .uri("http://localhost:8080/api/jpa/shares")
                .accept(MediaType.APPLICATION_JSON)
                .attributes(clientRegistrationId("my-client"))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return ResponseEntity.ok(shares);
    }


    @GetMapping("/healthCheck")
    public String healthCheck() {
        String message =  this.restClient.get()
                .uri("http://localhost:8080/api/jpa/shares/healthCheck")
                .accept(MediaType.APPLICATION_JSON)
                .attributes(clientRegistrationId("my-client"))
                .retrieve()
                .body(String.class);


        return message;

    }


}
