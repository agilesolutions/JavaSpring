package com.agilesolutions.mongo.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.mongo.service.MongoDBShareService;
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

@Tag(
        name = "CRUD REST APIs to CREATE, READ, UPDATE, DELETE shares",
        description = "CRUD REST APIs for managing shares using MongoDB"
)
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/mongo/shares")
public class MongoDBShareController {

    private final MongoDBShareService shareService;

    @Operation(
            summary = "Fetch all shares from the ",
            description = "REST API to fetch all shares from the database"
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
    @GetMapping
    public ResponseEntity<List<ShareDTO>> getAllShares() {
        log.info("Get all shares");

        return ResponseEntity.ok(shareService.getAllShares());
    }
}