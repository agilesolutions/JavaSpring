package com.agilesolutions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Schema(name = "Share", description = "Share model")
@Builder
public record ShareDTO(
        @NotEmpty(message = "Company name cannot be empty")
        String company,
        @PositiveOrZero(message = "Quantity must be zero or positive")
        Integer quantity) {
}