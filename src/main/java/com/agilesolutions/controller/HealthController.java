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

/**
 * REST controller providing an endpoint to switch the application's health state to unhealthy.
 * <p>
 * This is typically used by Kubernetes preStop hooks to gracefully remove the pod from service.
 * When the /unhealthy endpoint is called, the pod's health status is set to DOWN and the process waits
 * for a configured termination grace period to allow for graceful shutdown.
 * </p>
 */
@Tag(
        name = "REST API to switch POD to unhealthy state and trigger kubernetes preStop hook to gracefully remove POD from service",
        description = "REST API to switch POD to unhealthy state and trigger kubernetes preStop hook to gracefully remove POD from service"
)
@Slf4j
@RestController
@AllArgsConstructor
public class HealthController {

    private final HealthService healthService;

    /**
     * Switches the application's health state to unhealthy.
     * <p>
     * This endpoint is intended to be called by Kubernetes preStop hooks. It sets the application's health
     * status to DOWN and waits for the termination grace period (15 seconds) to allow for graceful removal
     * of the pod from service. During this time, the pod is removed from the service endpoints and new
     * traffic is prevented from reaching it.
     * </p>
     * <b>Access restricted to users with ADMIN role.</b>
     */
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
    public void unhealthy() {
        log.info("Switching to unhealthy state");
        healthService.unhealthy();
    }

}