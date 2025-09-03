package com.agilesolutions.metrics;

import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemMetricsConfig {

    @Bean
    JvmMemoryMetrics memoryMetrics() {
        return new JvmMemoryMetrics();
    }

    @Bean
    ProcessorMetrics cpuMetrics() {
        return new ProcessorMetrics();
    }

    @Bean
    JvmGcMetrics gcMetrics() {
        return new JvmGcMetrics();
    }
}
