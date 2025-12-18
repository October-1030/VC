package com.vaultcard.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO for creating a subscription profile with a linked virtual card.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubscriptionRequest {

    /**
     * User-friendly name for this subscription
     * e.g., "Netflix", "Spotify", "ChatGPT Plus"
     */
    @NotBlank(message = "Nickname is required")
    @Size(min = 1, max = 100, message = "Nickname must be between 1 and 100 characters")
    private String nickname;

    /**
     * Monthly spending limit in USD
     */
    @NotNull(message = "Monthly limit is required")
    @DecimalMin(value = "1.00", message = "Minimum monthly limit is 1.00")
    private BigDecimal monthlyLimit;

    /**
     * Optional: Allowed merchant category codes (comma-separated)
     */
    private String allowedMccCodes;

    /**
     * Optional: Notes about this subscription
     */
    private String notes;
}
