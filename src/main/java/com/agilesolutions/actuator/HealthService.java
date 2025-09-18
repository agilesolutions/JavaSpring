package com.agilesolutions.actuator;

import org.springframework.stereotype.Service;

/**
 * Service for managing the application's health status.
 * <p>
 * Provides methods to set the application as healthy or unhealthy, typically used for integration
 * with Kubernetes readiness and liveness probes.
 * </p>
 */
@Service
public class HealthService {
    private boolean healthy = true;

    /**
     * Returns the current health status of the application.
     *
     * @return {@code true} if healthy, {@code false} otherwise
     */
    public boolean isHealthy() { return healthy; }

    /**
     * Sets the application's health status.
     *
     * @param healthy {@code true} to mark as healthy, {@code false} to mark as unhealthy
     */
    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    /**
     * Marks the application as unhealthy and waits for 15 seconds.
     * <p>
     * Intended to be called before pod termination to allow for graceful shutdown,
     * such as closing connections or logging.
     * </p>
     */
    public void unhealthy() {
        setHealthy(false);
        try {
            // Here, we can code some features we want before the waiting process (connections closing, logging, etc.).
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Marks the application as healthy and waits for 15 seconds.
     * <p>
     * Can be used to simulate a delay when transitioning to a healthy state.
     * </p>
     */
    public void healthy() {
        setHealthy(true);
        try {
            Thread.sleep(15000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}