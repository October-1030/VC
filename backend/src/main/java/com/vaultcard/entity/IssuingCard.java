package com.vaultcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * IssuingCard entity - represents a Stripe Issuing virtual card.
 *
 * IMPORTANT: This entity does NOT store sensitive card data (full PAN, CVC).
 * Only Stripe-safe identifiers and masked data are stored.
 * All sensitive card operations go through Stripe API.
 */
@Entity
@Table(name = "issuing_cards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssuingCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * Stripe Issuing Card ID (e.g., "ic_xxx")
     * This is the primary identifier for Stripe API calls
     */
    @Column(name = "stripe_card_id", unique = true)
    private String stripeCardId;

    /**
     * Last 4 digits of card number (safe to store and display)
     */
    @Column(length = 4)
    private String last4;

    /**
     * Card brand (visa, mastercard)
     */
    private String brand;

    /**
     * Card expiration month (1-12)
     */
    @Column(name = "exp_month")
    private Integer expMonth;

    /**
     * Card expiration year (e.g., 2028)
     */
    @Column(name = "exp_year")
    private Integer expYear;

    /**
     * Card type: virtual or physical
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    @Builder.Default
    private CardType cardType = CardType.VIRTUAL;

    /**
     * Card status from Stripe
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CardStatus status = CardStatus.INACTIVE;

    /**
     * Spending limit per transaction (in cents)
     */
    @Column(name = "spending_limit_per_transaction")
    private Long spendingLimitPerTransaction;

    /**
     * Spending limit per month (in cents)
     */
    @Column(name = "spending_limit_per_month")
    private Long spendingLimitPerMonth;

    /**
     * Optional: Card nickname for identification
     */
    private String nickname;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

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
     * Card type enum
     */
    public enum CardType {
        VIRTUAL,
        PHYSICAL
    }

    /**
     * Card status enum (mirrors Stripe Issuing card status)
     */
    public enum CardStatus {
        INACTIVE,   // Card created but not yet activated
        ACTIVE,     // Card is active and can be used
        FROZEN,     // Card is temporarily frozen
        CANCELED    // Card is permanently canceled
    }
}
