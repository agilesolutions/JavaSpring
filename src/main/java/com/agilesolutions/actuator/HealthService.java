package com.agilesolutions.actuator;

import org.springframework.stereotype.Service;

@Service
public class HealthService {
    private boolean healthy = true;

    public boolean isHealthy() { return healthy; }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public void unhealthy() {
        setHealthy(false);
        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void healthy() {
        setHealthy(true);
        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}