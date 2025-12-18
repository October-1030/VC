package com.vaultcard.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request DTO for creating a funding transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFundingRequest {

    /**
     * Amount to fund (in major currency units, e.g., dollars)
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Minimum funding amount is 1.00")
    private BigDecimal amount;

    /**
     * Currency code (ISO 4217), defaults to "usd"
     */
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    @Builder.Default
    private String currency = "usd";

    /**
     * Optional: preferred payment method type (card, alipay, wechat_pay)
     */
    private String paymentMethodType;
}
