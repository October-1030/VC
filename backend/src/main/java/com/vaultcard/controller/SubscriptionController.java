package com.vaultcard.controller;

import com.vaultcard.dto.ApiResponse;
import com.vaultcard.dto.CreateSubscriptionRequest;
import com.vaultcard.dto.SubscriptionListResponse;
import com.vaultcard.dto.SubscriptionResponse;
import com.vaultcard.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for subscription profile operations.
 *
 * Manages subscription profiles and their linked virtual cards.
 * Each subscription profile is linked to a dedicated card with spending controls.
 *
 * This is an internal prototype API - not for public use.
 */
@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Slf4j
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Create a new subscription profile with a linked virtual card.
     *
     * POST /api/subscriptions
     *
     * Creates:
     * 1. A SubscriptionProfile with nickname and monthly limit
     * 2. A linked virtual card via Stripe Issuing (with spending controls)
     *
     * @param request Subscription creation request
     * @return SubscriptionResponse with profile and card info
     */
    @PostMapping
    public ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(
            @Valid @RequestBody CreateSubscriptionRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        log.info("POST /api/subscriptions - nickname: {}, monthlyLimit: {}",
                request.getNickname(), request.getMonthlyLimit());

        // TODO: Get userId from authenticated session/JWT token
        if (userId == null || userId.isBlank()) {
            userId = "test-user-001";
        }

        try {
            SubscriptionResponse response = subscriptionService.createSubscription(userId, request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            log.warn("Subscription creation failed - bad request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("BAD_REQUEST", e.getMessage()));
        } catch (IllegalStateException e) {
            log.warn("Subscription creation failed - state error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("INVALID_STATE", e.getMessage()));
        } catch (Exception e) {
            log.error("Subscription creation failed - unexpected error", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("INTERNAL_ERROR", "An unexpected error occurred"));
        }
    }

    /**
     * Get all subscriptions for the current user.
     *
     * GET /api/subscriptions
     *
     * Returns list of subscription profiles with summary statistics.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SubscriptionListResponse>> getUserSubscriptions(
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        log.info("GET /api/subscriptions - userId: {}", userId);

        // TODO: Get userId from authenticated session/JWT token
        if (userId == null || userId.isBlank()) {
            userId = "test-user-001";
        }

        SubscriptionListResponse response = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * Get a specific subscription by ID.
     *
     * GET /api/subscriptions/{subscriptionId}
     */
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> getSubscription(
            @PathVariable String subscriptionId) {

        log.info("GET /api/subscriptions/{}", subscriptionId);

        try {
            SubscriptionResponse response = subscriptionService.getSubscription(subscriptionId);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Pause a subscription (freeze the linked card).
     *
     * POST /api/subscriptions/{subscriptionId}/pause
     */
    @PostMapping("/{subscriptionId}/pause")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> pauseSubscription(
            @PathVariable String subscriptionId) {

        log.info("POST /api/subscriptions/{}/pause", subscriptionId);

        try {
            SubscriptionResponse response = subscriptionService.pauseSubscription(subscriptionId);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("INVALID_STATE", e.getMessage()));
        }
    }

    /**
     * Resume a paused subscription (unfreeze the linked card).
     *
     * POST /api/subscriptions/{subscriptionId}/resume
     */
    @PostMapping("/{subscriptionId}/resume")
    public ResponseEntity<ApiResponse<SubscriptionResponse>> resumeSubscription(
            @PathVariable String subscriptionId) {

        log.info("POST /api/subscriptions/{}/resume", subscriptionId);

        try {
            SubscriptionResponse response = subscriptionService.resumeSubscription(subscriptionId);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("INVALID_STATE", e.getMessage()));
        }
    }
}
