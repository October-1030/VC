package com.vaultcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * IssuingTransaction entity - represents a transaction made with a Stripe Issuing card.
 *
 * Tracks all card transactions (authorizations and captures) received via Stripe webhooks.
 * IMPORTANT: Never store sensitive card data. Only use Stripe transaction IDs.
 */
@Entity
@Table(name = "issuing_transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * Stripe Issuing Transaction ID (e.g., "ipi_xxx")
     */
    @Column(name = "stripe_transaction_id", unique = true)
    private String stripeTransactionId;

    /**
     * Stripe Issuing Authorization ID (e.g., "iauth_xxx")
     * Links to the original authorization request
     */
    @Column(name = "stripe_authorization_id")
    private String stripeAuthorizationId;

    /**
     * Stripe Card ID this transaction belongs to
     */
    @Column(name = "stripe_card_id", nullable = false)
    private String stripeCardId;

    /**
     * Transaction amount in smallest currency unit (e.g., cents)
     * Positive for purchases, negative for refunds
     */
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * ISO 4217 currency code (e.g., "usd")
     */
    @Column(nullable = false, length = 3)
    private String currency;

    /**
     * Merchant name from the transaction
     */
    @Column(name = "merchant_name")
    private String merchantName;

    /**
     * Merchant category code (MCC)
     */
    @Column(name = "merchant_category_code", length = 4)
    private String merchantCategoryCode;

    /**
     * Merchant city
     */
    @Column(name = "merchant_city")
    private String merchantCity;

    /**
     * Merchant country (ISO 3166-1 alpha-2)
     */
    @Column(name = "merchant_country", length = 2)
    private String merchantCountry;

    /**
     * Transaction type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    @Builder.Default
    private TransactionType transactionType = TransactionType.PURCHASE;

    /**
     * Transaction status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.PENDING;

    /**
     * Decline reason if transaction was declined
     */
    @Column(name = "decline_reason")
    private String declineReason;

    /**
     * Timestamp from Stripe when transaction was created
     */
    @Column(name = "stripe_created_at")
    private LocalDateTime stripeCreatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
     * Transaction type enum
     */
    public enum TransactionType {
        PURCHASE,           // Regular purchase
        REFUND,             // Refund from merchant
        DISPUTE,            // Disputed transaction
        CASH_WITHDRAWAL,    // ATM withdrawal (if physical card)
        FORCE_CAPTURE       // Force captured authorization
    }

    /**
     * Transaction status enum
     */
    public enum TransactionStatus {
        PENDING,    // Authorization pending
        APPROVED,   // Transaction approved
        DECLINED,   // Transaction declined
        REVERSED,   // Transaction reversed/voided
        CAPTURED    // Authorization captured
    }
}
