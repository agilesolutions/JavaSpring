package com.agilesolutions.jpa.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider",
        dateTimeProviderRef = "dateTimeProvider")
public class AuditConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
// Implement logic to provide current auditor (user)
        return new AuditorAwareImpl();
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        // Implement logic to provide current date and time
        return () -> Optional.of(LocalDateTime.now());
    }

}