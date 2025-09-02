package com.agilesolutions.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomMetrics {

    private final MeterRegistry registry;
    private Counter ordersCounter;
    private Timer processTimer;
    private AtomicInteger activeUsersGauge;

    public CustomMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    void init() {
        // Counter
        ordersCounter = Counter.builder("app.orders.placed")
                .description("Number of orders placed")
                .tag("type", "online")
                .register(registry);

        // Timer
        processTimer = Timer.builder("app.process.time")
                .description("Time taken to process something")
                .publishPercentiles(0.5, 0.95)  // optional
                .register(registry);

        // Gauge
        activeUsersGauge = registry.gauge("app.active.users",
                new AtomicInteger(0));
    }

    public void incrementOrders() {
        ordersCounter.increment();
    }

    public void recordProcess(Runnable task) {
        processTimer.record(task);
    }

    public void updateActiveUsers(int value) {
        activeUsersGauge.set(value);
    }
}