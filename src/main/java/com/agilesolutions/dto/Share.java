package com.agilesolutions.dto;

import lombok.Builder;

@Builder
public record Share(Integer id, String company, Integer Quantity) {
}
