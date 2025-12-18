package com.vaultcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * SubscriptionProfile entity - represents a subscription payment profile.
 *
 * Each user can have multiple subscription profiles, each linked to a virtual card.
 * This allows users to manage different subscriptions (Netflix, Spotify, etc.)
 * with separate cards and spending limits.
 */
@Entity
@Table(name = "subscription_profiles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    /**
     * User-friendly name for this subscription profile
     * e.g., "Netflix", "Spotify", "ChatGPT"
     */
    @Column(nullable = false)
    private String nickname;

    /**
     * Linked Stripe Issuing card ID
     * This is the card used for this subscription profile
     */
    @Column(name = "linked_card_id")
    private String linkedCardId;

    /**
     * Monthly spending limit in USD (or base currency)
     * Used for spend control on the linked card
     */
    @Column(name = "monthly_limit", precision = 19, scale = 2)
    private BigDecimal monthlyLimit;

    /**
     * Current month's spending total (calculated/cached)
     */
    @Column(name = "current_month_spent", precision = 19, scale = 2)
    @Builder.Default
    private BigDecimal currentMonthSpent = BigDecimal.ZERO;

    /**
     * Profile status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ProfileStatus status = ProfileStatus.ACTIVE;

    /**
     * Optional: Target merchant category codes (MCCs) for this profile
     * Stored as comma-separated values, e.g., "5815,5816" for streaming services
     */
    @Column(name = "allowed_mcc_codes")
    private String allowedMccCodes;

    /**
     * Optional: Notes or description
     */
    private String notes;

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
     * Profile status enum
     */
    public enum ProfileStatus {
        ACTIVE,     // Profile is active and card can be used
        PAUSED,     // Profile is paused (card frozen)
        CLOSED      // Profile is closed
    }
}
