package com.vaultcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * FundingTransaction entity - represents a funding/top-up transaction.
 *
 * Tracks user deposits into their VaultCard balance via Stripe Payments.
 * Supports multiple payment methods (card, Alipay, etc.) via Stripe.
 */
@Entity
@Table(name = "funding_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * Stripe PaymentIntent ID or Checkout Session ID
     * IMPORTANT: Never store full card numbers or sensitive payment data
     */
    @Column(name = "stripe_payment_id")
    private String stripePaymentId;

    /**
     * Stripe PaymentIntent client secret (for frontend to confirm payment)
     * This is temporary and only used during the payment flow
     */
    @Column(name = "stripe_client_secret")
    private String stripeClientSecret;

    /**
     * Amount in smallest currency unit (e.g., cents for USD)
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * ISO 4217 currency code (e.g., "usd", "cny")
     */
    @Column(nullable = false, length = 3)
    private String currency;

    /**
     * Payment method type (card, alipay, wechat_pay, etc.)
     */
    @Column(name = "payment_method_type")
    private String paymentMethodType;

    /**
     * Transaction status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private FundingStatus status = FundingStatus.PENDING;

    /**
     * Error message if payment failed
     */
    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Funding transaction status
     */
    public enum FundingStatus {
        PENDING,        // Payment intent created, awaiting payment
        PROCESSING,     // Payment is being processed
        SUCCEEDED,      // Payment completed successfully
        FAILED,         // Payment failed
        CANCELED,       // Payment was canceled
        REFUNDED        // Payment was refunded
    }
}
