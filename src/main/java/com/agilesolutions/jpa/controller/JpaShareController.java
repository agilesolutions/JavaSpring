package com.agilesolutions.jpa.controller;

import com.agilesolutions.dto.ShareDTO;
import com.agilesolutions.exception.BusinessException;
import com.agilesolutions.jpa.model.Share;
import com.agilesolutions.jpa.service.JpaShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs to CREATE, READ, UPDATE, DELETE shares",
        description = "CRUD REST APIs for managing shares using JPA"
)
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/jpa/shares")
public class JpaShareController {

    private final JpaShareService shareService;

    @Operation(
            summary = "REST API to check health of the application",
            description = "REST API to check health of the application"
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
    @GetMapping("/healthCheck")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> healthCheck() {
        log.info("api endpoint");
        return ResponseEntity.ok("Application is running fine");
    }


    @Operation(
            summary = "Fetch all shares",
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
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ShareDTO>> getAllShares() {
        log.info("Get all shares");
        List shares = shareService.getAllShares();

        return ResponseEntity.status(HttpStatus.OK).body(shares);

    }

    @Operation(
            summary = "Fetch share by ID",
            description = "REST API to fetch a share by its ID from the database"
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
    @GetMapping("/{id}")
    public ResponseEntity<ShareDTO> getShareById(@PathVariable Long id) {
        log.info("Get share by ID: {}", id);
        return shareService.getShareById(id)
                .map(s -> ResponseEntity.ok(ShareDTO.builder().Quantity(s.getQuantity()).company(s.getCompany()).build()))
                .orElseThrow(() -> new BusinessException("Share with ID " + id + " not found"));
    }

    @Operation(
            summary = "Create a new share",
            description = "REST API to create a new share in the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
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
    @PostMapping
    public ResponseEntity<ShareDTO> createShare(@RequestBody Share share) {
        log.info("Create new share: {}", share);
        Share createdShare = shareService.createShare(share);
        return ResponseEntity.status(HttpStatus.CREATED).body(ShareDTO.builder().company(createdShare.getCompany()).Quantity(createdShare.getQuantity()).build());
    }

    @Operation(
            summary = "Update share by ID",
            description = "REST API to update a share by its ID in the database"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @PutMapping("/{id}")
    public ResponseEntity<ShareDTO> updateShare(@PathVariable Long id, @RequestBody Share share) {
        log.info("Update share with ID: {}", id);
        return shareService.updateShare(id, share)
                .map(s -> ResponseEntity.ok(ShareDTO.builder().company(s.getCompany()).Quantity(s.getQuantity()).build()))
                .orElseThrow(() -> new BusinessException("Share with ID " + id + " not found"));
    }

    @Operation(
            summary = "Delete share by ID",
            description = "REST API to delete Customer &  Account details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShare(@PathVariable Long id) {
        log.info("Delete share with ID: {}", id);
        if (shareService.deleteShare(id)) {
            return ResponseEntity.noContent().build();
        } else {
            throw new BusinessException("Share with ID " + id + " not found");
        }
    }
}