package com.agilesolutions.dto;

import lombok.Builder;

@Builder
public record ShareDTO(String company, Integer quantity) {
}
