package com.agilesolutions.controller;

import com.agilesolutions.actuator.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "REST API to switch POD to unhealthy state and trigger kubernetes preStop hook to gracefully remove POD from service",
        description = "REST API to switch POD to unhealthy state and trigger kubernetes preStop hook to gracefully remove POD from service"
)
@Slf4j
@RestController
@AllArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @Operation(
            summary = "Switch POD to unhealthy state",
            description = "REST API to switch POD to unhealthy state and trigger kubernetes preStop hook to gracefully remove POD from service, removes IP of this pod from iptable and prevents new traffic from coming"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @GetMapping(value = "/unhealthy")
    @PreAuthorize("hasRole('ADMIN')")
    /**
     * The preStop hook calls the /unhealthy endpoint and sets the application’s health status to DOWN and waits here for the terminationGracePeriodSeconds value (15 sec.).
     * Call this endpoint only when you want to remove the pod from service gracefully.
     */
    public void unhealthy() {
        log.info("Switching to unhealthy state");
        healthService.unhealthy();
    }

}