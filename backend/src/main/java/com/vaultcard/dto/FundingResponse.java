package com.vaultcard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for funding transaction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingResponse {

    /**
     * Internal transaction ID
     */
    private String transactionId;

    /**
     * Stripe PaymentIntent client secret
     * Frontend uses this to confirm the payment
     */
    private String clientSecret;

    /**
     * Stripe PaymentIntent ID
     */
    private String stripePaymentId;

    /**
     * Amount in major currency units
     */
    private BigDecimal amount;

    /**
     * Currency code
     */
    private String currency;

    /**
     * Transaction status
     */
    private String status;

    /**
     * Checkout URL (if using Stripe Checkout)
     */
    private String checkoutUrl;

    /**
     * Created timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Error message if any
     */
    private String errorMessage;
}
