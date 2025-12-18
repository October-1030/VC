package com.vaultcard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for listing all user subscriptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionListResponse {

    /**
     * List of subscription profiles
     */
    private List<SubscriptionResponse> subscriptions;

    /**
     * Summary statistics
     */
    private Summary summary;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Summary {
        /**
         * Total number of active subscriptions
         */
        private int totalActive;

        /**
         * Total monthly limit across all subscriptions
         */
        private BigDecimal totalMonthlyLimit;

        /**
         * Total spent this month across all subscriptions
         */
        private BigDecimal totalSpentThisMonth;

        /**
         * Total remaining budget
         */
        private BigDecimal totalRemaining;
    }
}
