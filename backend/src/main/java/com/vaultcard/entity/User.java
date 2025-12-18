package com.vaultcard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * User entity - represents an internal/whitelist user of the VaultCard system.
 *
 * This is for the internal developer prototype only.
 * Users are provisioned internally, not through public registration.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    /**
     * ISO 3166-1 alpha-2 country code (e.g., "US", "CN")
     */
    @Column(length = 2)
    private String country;

    /**
     * KYC verification status.
     * For internal prototype, this is managed manually.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private KycStatus kycStatus = KycStatus.PENDING;

    /**
     * Stripe Customer ID - created when user first interacts with Stripe
     */
    @Column(name = "stripe_customer_id")
    private String stripeCustomerId;

    /**
     * Stripe Cardholder ID - for Issuing API
     */
    @Column(name = "stripe_cardholder_id")
    private String stripeCardholderId;

    /**
     * Whether user is active (whitelist controlled)
     */
    @Builder.Default
    private boolean active = true;

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
     * KYC Status enum
     */
    public enum KycStatus {
        PENDING,    // Not yet verified
        VERIFIED,   // KYC passed
        REJECTED    // KYC failed
    }
}
