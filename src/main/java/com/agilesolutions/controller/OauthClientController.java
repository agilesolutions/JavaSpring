package com.agilesolutions.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.dto.StockResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.client.RequestAttributeClientRegistrationIdResolver.clientRegistrationId;

@Tag(
        name = "CRUD REST APIs demonstrating OAuth2 Client",
        description = "CRUD REST APIs demonstrating OAuth2 Client"
)

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

    @Operation(
            summary = "Fetch all shares using OAuth2 Client",
            description = "Fetch all shares using OAuth2 Client"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
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


    @Operation(
            summary = "health check using OAuth2 Client",
            description = "health check using OAuth2 Client"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        String message =  this.restClient.get()
                .uri("http://localhost:8080/api/jpa/shares/healthCheck")
                .accept(MediaType.APPLICATION_JSON)
                .attributes(clientRegistrationId("my-client"))
                .retrieve()
                .body(String.class);


        return ResponseEntity.ok(message);

    }


}
