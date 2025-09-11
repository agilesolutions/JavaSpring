package com.agilesolutions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Schema(name = "StockResponse", description = "Stock response model")
@Builder
public record StockDto(
        @PositiveOrZero(message = "Price must be zero or positive")
        Float price) {
}