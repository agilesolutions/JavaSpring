package com.agilesolutions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AccountDto (
        @NotEmpty(message = "AccountNumber can not be a null or empty")
        @Pattern(regexp="(^$|[0-9]{10})",message = "AccountNumber must be 10 digits")
        @Schema(
                description = "Account Number of Eazy Bank account", example = "3454433243"
        )
        String accountNumber,
        @NotEmpty(message = "AccountType can not be a null or empty")
        @Schema(
                description = "Account type of Eazy Bank account", example = "Savings"
        )
        String accountType,
        @NotEmpty(message = "BranchAddress can not be a null or empty")
        @Schema(
                description = "Eazy Bank branch address", example = "123 NewYork"
        )
        String branchAddress
) {}
