package com.agilesolutions.kafka.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.kafka.service.KafkaShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(
        name = "CRUD REST APIs to fetch shares from Kafka topic",
        description = "CRUD REST APIs to fetch shares from Kafka topic"
)
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/kafka/shares")
public class KafkaShareController {

    private final KafkaShareService shareService;

    @Operation(
            summary = "Fetch all shares from Kafka topic",
            description = "REST API to fetch all shares from Kafka topic"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @GetMapping(produces = "application/json")
    ResponseEntity<List<ShareDTO>> getAllShares() {
        log.info("Get all shares");
        List shares =  shareService.getAllShares()
                .stream()
                .map(s -> ShareDTO.builder()
                        .company(s.getCompany())
                        .quantity(s.getQuantity())
                        .build())
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok(shares);

    }
}