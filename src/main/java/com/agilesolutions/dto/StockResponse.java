package com.agilesolutions.dto;

import lombok.Builder;

@Builder
public record StockResponse(Float price) {
}