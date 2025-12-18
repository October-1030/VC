package com.vaultcard.controller;

import com.vaultcard.dto.ApiResponse;
import com.vaultcard.dto.CreateFundingRequest;
import com.vaultcard.dto.FundingResponse;
import com.vaultcard.entity.FundingTransaction;
import com.vaultcard.service.FundingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST Controller for funding/top-up operations.
 *
 * Handles user deposits into VaultCard balance via Stripe Payments.
 *
 * This is an internal prototype API - not for public use.
 */
@RestController
@RequestMapping("/api/funding")
@RequiredArgsConstructor
@Slf4j
public class FundingController {

    private final FundingService fundingService;

    /**
     * Create a new funding transaction.
     *
     * POST /api/funding
     *
     * Creates a Stripe PaymentIntent and returns the client secret
     * for the frontend to complete the payment.
     *
     * @param request Funding request with amount and currency
     * @return FundingResponse with client secret for payment confirmation
     */
    @PostMapping
    public ResponseEntity<ApiResponse<FundingResponse>> createFunding(
            @Valid @RequestBody CreateFundingRequest request,
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        log.info("POST /api/funding - amount: {} {}", request.getAmount(), request.getCurrency());

        // TODO: Get userId from authenticated session/JWT token
        // For now, use header or default test user
        if (userId == null || userId.isBlank()) {
            userId = "test-user-001"; // Default test user for development
        }

        try {
            FundingResponse response = fundingService.createFunding(userId, request);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            log.warn("Funding creation failed - bad request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("BAD_REQUEST", e.getMessage()));
        } catch (IllegalStateException e) {
            log.warn("Funding creation failed - state error: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("INVALID_STATE", e.getMessage()));
        } catch (Exception e) {
            log.error("Funding creation failed - unexpected error", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("INTERNAL_ERROR", "An unexpected error occurred"));
        }
    }

    /**
     * Get funding transaction by ID.
     *
     * GET /api/funding/{transactionId}
     */
    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<FundingResponse>> getFunding(
            @PathVariable String transactionId) {

        log.info("GET /api/funding/{}", transactionId);

        try {
            FundingTransaction transaction = fundingService.getFundingTransaction(transactionId);

            FundingResponse response = FundingResponse.builder()
                    .transactionId(transaction.getId())
                    .stripePaymentId(transaction.getStripePaymentId())
                    .amount(transaction.getAmount())
                    .currency(transaction.getCurrency())
                    .status(transaction.getStatus().name())
                    .createdAt(transaction.getCreatedAt())
                    .errorMessage(transaction.getErrorMessage())
                    .build();

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all funding transactions for the current user.
     *
     * GET /api/funding
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<FundingTransaction>>> getUserFundings(
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        log.info("GET /api/funding - userId: {}", userId);

        // TODO: Get userId from authenticated session/JWT token
        if (userId == null || userId.isBlank()) {
            userId = "test-user-001";
        }

        List<FundingTransaction> transactions = fundingService.getUserFundingTransactions(userId);
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }

    /**
     * Get total funded amount for the current user.
     *
     * GET /api/funding/total
     */
    @GetMapping("/total")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalFunded(
            @RequestHeader(value = "X-User-Id", required = false) String userId) {

        log.info("GET /api/funding/total - userId: {}", userId);

        if (userId == null || userId.isBlank()) {
            userId = "test-user-001";
        }

        BigDecimal total = fundingService.getTotalFundedAmount(userId);
        return ResponseEntity.ok(ApiResponse.success(total));
    }
}
