package com.agilesolutions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Schema(name = "Share", description = "Share model")
@Builder
public record ShareDto(
        @NotEmpty(message = "Company name cannot be empty")
        @Schema(
                description = "TwelveData Data Asset owning companing like AAPL Apple Inc. ", example = "AAPL"
        )
        String company,
        @PositiveOrZero(message = "Quantity must be zero or positive")
        @Schema(
                description = "Number shares. ", example = "10"
        )
        Integer quantity) {
}