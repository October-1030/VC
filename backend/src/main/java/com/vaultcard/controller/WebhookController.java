package com.vaultcard.controller;

import com.vaultcard.service.StripeWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST Controller for Stripe webhook handling.
 *
 * This is the unified entry point for all Stripe webhook events:
 * - Payment events (payment_intent.succeeded, payment_intent.payment_failed)
 * - Issuing events (issuing_authorization.request, issuing_transaction.created, etc.)
 *
 * IMPORTANT:
 * - In production, webhook signature must be verified
 * - Never log sensitive data from webhook payloads
 * - issuing_authorization.request requires real-time response
 */
@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final StripeWebhookService stripeWebhookService;

    /**
     * Stripe webhook endpoint.
     *
     * POST /api/webhooks/stripe
     *
     * Receives and processes all Stripe webhook events.
     *
     * @param payload   Raw JSON payload from Stripe
     * @param signature Stripe-Signature header for verification
     * @return 200 OK if processed successfully, error code otherwise
     */
    @PostMapping("/stripe")
    public ResponseEntity<Map<String, Object>> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String signature) {

        log.info("POST /api/webhooks/stripe - received webhook");

        // TODO: In production, signature verification is REQUIRED
        // The signature header is used to verify the webhook came from Stripe
        if (signature == null || signature.isBlank()) {
            log.warn("Webhook received without signature - accepting in test mode only");
            // In production: return ResponseEntity.badRequest().body(Map.of("error", "Missing signature"));
        }

        try {
            StripeWebhookService.WebhookProcessingResult result =
                    stripeWebhookService.processWebhook(payload, signature);

            if (result.isSuccess()) {
                log.info("Webhook processed successfully: {}", result.getMessage());

                // For issuing_authorization.request, return approval decision
                if (result.getAuthorizationApproved() != null) {
                    return ResponseEntity.ok(Map.of(
                            "approved", result.getAuthorizationApproved(),
                            "message", result.getMessage()
                    ));
                }

                return ResponseEntity.ok(Map.of(
                        "received", true,
                        "message", result.getMessage()
                ));
            } else {
                log.error("Webhook processing failed: {}", result.getMessage());
                return ResponseEntity.badRequest().body(Map.of(
                        "error", result.getMessage()
                ));
            }
        } catch (Exception e) {
            log.error("Unexpected error processing webhook", e);
            // Return 200 to prevent Stripe retries for unrecoverable errors
            // Return 500 only for transient errors that should be retried
            return ResponseEntity.ok(Map.of(
                    "received", true,
                    "error", "Processing error - will not retry"
            ));
        }
    }

    /**
     * Health check endpoint for webhooks.
     *
     * GET /api/webhooks/health
     *
     * Used to verify the webhook endpoint is reachable.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> webhookHealth() {
        return ResponseEntity.ok(Map.of(
                "status", "healthy",
                "endpoint", "/api/webhooks/stripe",
                "message", "Webhook endpoint is ready to receive Stripe events"
        ));
    }
}
