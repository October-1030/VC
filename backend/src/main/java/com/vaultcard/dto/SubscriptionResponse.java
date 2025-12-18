package com.vaultcard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for subscription profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {

    /**
     * Subscription profile ID
     */
    private String id;

    /**
     * User-friendly name
     */
    private String nickname;

    /**
     * Monthly spending limit
     */
    private BigDecimal monthlyLimit;

    /**
     * Current month's spending
     */
    private BigDecimal currentMonthSpent;

    /**
     * Remaining budget this month
     */
    private BigDecimal remainingBudget;

    /**
     * Profile status
     */
    private String status;

    /**
     * Linked card information (simplified, no sensitive data)
     */
    private CardSummary linkedCard;

    /**
     * Created timestamp
     */
    private LocalDateTime createdAt;

    /**
     * Notes
     */
    private String notes;

    /**
     * Simplified card info (safe to display)
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardSummary {
        /**
         * Card ID (internal)
         */
        private String cardId;

        /**
         * Last 4 digits only (safe to display)
         */
        private String last4;

        /**
         * Card brand (visa, mastercard)
         */
        private String brand;

        /**
         * Expiration (e.g., "12/28")
         */
        private String expiration;

        /**
         * Card status
         */
        private String status;
    }
}
