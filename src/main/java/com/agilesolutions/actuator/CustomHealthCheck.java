package com.agilesolutions.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomHealthCheck extends AbstractHealthIndicator {
    @Autowired
    private HealthService healthService;

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