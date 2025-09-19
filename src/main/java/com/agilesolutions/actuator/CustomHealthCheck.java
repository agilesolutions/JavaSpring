package com.agilesolutions.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * Custom health indicator for application-specific health checks.
 * <p>
 * This component uses {@link HealthService} to determine the application's health status.
 * It reports {@code UP} if the service is healthy, otherwise {@code DOWN}.
 * </p>
 */
@Component
@Slf4j
public class CustomHealthCheck extends AbstractHealthIndicator {
    @Autowired
    private HealthService healthService;

    /**
     * Performs a health check using {@link HealthService}.
     * Sets the health status to {@code UP} if healthy, otherwise {@code DOWN}.
     *
     * @param health the health builder to report status and details
     */
    @Override
    protected void doHealthCheck(Health.Builder health) {
        if (healthService.isHealthy()) {
            //log.info("health status switch to up");
            health.up();
        } else {
            //log.info("health status switch to down");
            health.down();
        }
    }
}