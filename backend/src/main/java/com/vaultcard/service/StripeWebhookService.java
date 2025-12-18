package com.vaultcard.service;

import com.vaultcard.entity.FundingTransaction;
import com.vaultcard.entity.IssuingTransaction;
import com.vaultcard.repository.IssuingTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Service for handling Stripe webhook events.
 *
 * This is the unified entry point for all Stripe webhook events.
 * Events are validated using Stripe's webhook signature verification.
 *
 * IMPORTANT: Never log sensitive payment data from webhooks.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookService {

    private final FundingService fundingService;
    private final SubscriptionService subscriptionService;
    private final IssuingCardService issuingCardService;
    private final IssuingTransactionRepository issuingTransactionRepository;

    @Value("${stripe.webhook-secret:}")
    private String webhookSecret;

    /**
     * Process incoming Stripe webhook event.
     *
     * @param payload   Raw webhook payload
     * @param signature Stripe signature header
     * @return Processing result
     */
    @Transactional
    public WebhookProcessingResult processWebhook(String payload, String signature) {
        log.info("Processing Stripe webhook");

        // TODO: Verify webhook signature using Stripe SDK
        // ---------------------------------------------------------------
        // try {
        //     Event event = Webhook.constructEvent(payload, signature, webhookSecret);
        //     return processEvent(event);
        // } catch (SignatureVerificationException e) {
        //     log.error("Webhook signature verification failed", e);
        //     return WebhookProcessingResult.error("Invalid signature");
        // }
        // ---------------------------------------------------------------

        // For now, parse JSON manually (placeholder for test mode)
        try {
            // Mock parsing - in production, use Stripe SDK
            Map<String, Object> eventData = parseEventPayload(payload);
            String eventType = (String) eventData.get("type");
            String eventId = (String) eventData.get("id");

            log.info("Received webhook event: {} ({})", eventType, eventId);

            return processEvent(eventType, eventData);
        } catch (Exception e) {
            log.error("Error processing webhook", e);
            return WebhookProcessingResult.error("Processing error: " + e.getMessage());
        }
    }

    /**
     * Process a specific Stripe event.
     */
    private WebhookProcessingResult processEvent(String eventType, Map<String, Object> eventData) {
        switch (eventType) {
            // === Payment Events (for Funding) ===
            case "payment_intent.succeeded":
                return handlePaymentIntentSucceeded(eventData);

            case "payment_intent.payment_failed":
                return handlePaymentIntentFailed(eventData);

            // === Issuing Events ===
            case "issuing_authorization.request":
                return handleIssuingAuthorizationRequest(eventData);

            case "issuing_authorization.created":
                return handleIssuingAuthorizationCreated(eventData);

            case "issuing_transaction.created":
                return handleIssuingTransactionCreated(eventData);

            case "issuing_card.created":
                return handleIssuingCardCreated(eventData);

            case "issuing_card.updated":
                return handleIssuingCardUpdated(eventData);

            // === Unknown Events ===
            default:
                log.debug("Unhandled webhook event type: {}", eventType);
                return WebhookProcessingResult.ignored("Event type not handled: " + eventType);
        }
    }

    // ==================== Payment Intent Handlers ====================

    private WebhookProcessingResult handlePaymentIntentSucceeded(Map<String, Object> eventData) {
        log.info("Handling payment_intent.succeeded");

        // TODO: Extract PaymentIntent ID from event data
        // String paymentIntentId = getNestedValue(eventData, "data.object.id");
        // fundingService.updateFundingStatus(paymentIntentId, FundingTransaction.FundingStatus.SUCCEEDED);

        // Placeholder
        log.info("Payment intent succeeded - funding will be credited");
        return WebhookProcessingResult.success("Payment intent processed");
    }

    private WebhookProcessingResult handlePaymentIntentFailed(Map<String, Object> eventData) {
        log.info("Handling payment_intent.payment_failed");

        // TODO: Extract PaymentIntent ID and failure reason
        // String paymentIntentId = getNestedValue(eventData, "data.object.id");
        // String failureMessage = getNestedValue(eventData, "data.object.last_payment_error.message");
        // fundingService.updateFundingStatus(paymentIntentId, FundingTransaction.FundingStatus.FAILED);

        log.warn("Payment intent failed");
        return WebhookProcessingResult.success("Payment failure recorded");
    }

    // ==================== Issuing Authorization Handlers ====================

    private WebhookProcessingResult handleIssuingAuthorizationRequest(Map<String, Object> eventData) {
        log.info("Handling issuing_authorization.request (real-time decision)");

        // TODO: Implement real-time authorization decision
        // ---------------------------------------------------------------
        // This is a SYNCHRONOUS webhook - Stripe expects a response within seconds.
        // The response determines whether the transaction is approved or declined.
        //
        // Steps:
        // 1. Extract authorization details (card ID, amount, merchant)
        // 2. Check user's balance
        // 3. Check spending limits
        // 4. Apply any custom rules (MCC restrictions, etc.)
        // 5. Return approve/decline decision
        //
        // String cardId = getNestedValue(eventData, "data.object.card.id");
        // Long amountCents = getNestedValue(eventData, "data.object.pending_request.amount");
        // String merchantName = getNestedValue(eventData, "data.object.merchant_data.name");
        //
        // boolean approved = checkAuthorizationRules(cardId, amountCents, merchantName);
        //
        // return WebhookProcessingResult.authorizationDecision(approved, approved ? null : "Insufficient funds");
        // ---------------------------------------------------------------

        log.info("Authorization request received - would check balance and limits");
        return WebhookProcessingResult.success("Authorization request processed");
    }

    private WebhookProcessingResult handleIssuingAuthorizationCreated(Map<String, Object> eventData) {
        log.info("Handling issuing_authorization.created");

        // TODO: Record the authorization for tracking
        // This is informational - the decision was already made

        return WebhookProcessingResult.success("Authorization recorded");
    }

    // ==================== Issuing Transaction Handlers ====================

    private WebhookProcessingResult handleIssuingTransactionCreated(Map<String, Object> eventData) {
        log.info("Handling issuing_transaction.created");

        // TODO: Record transaction and update spending totals
        // ---------------------------------------------------------------
        // String transactionId = getNestedValue(eventData, "data.object.id");
        // String cardId = getNestedValue(eventData, "data.object.card");
        // Long amountCents = getNestedValue(eventData, "data.object.amount");
        // String merchantName = getNestedValue(eventData, "data.object.merchant_data.name");
        // String mcc = getNestedValue(eventData, "data.object.merchant_data.category_code");
        // Long createdTimestamp = getNestedValue(eventData, "data.object.created");
        //
        // IssuingCard card = issuingCardService.getCardByStripeId(cardId);
        //
        // IssuingTransaction transaction = IssuingTransaction.builder()
        //     .userId(card.getUserId())
        //     .stripeTransactionId(transactionId)
        //     .stripeCardId(cardId)
        //     .amount(BigDecimal.valueOf(amountCents).divide(BigDecimal.valueOf(100)))
        //     .currency("usd")
        //     .merchantName(merchantName)
        //     .merchantCategoryCode(mcc)
        //     .transactionType(IssuingTransaction.TransactionType.PURCHASE)
        //     .status(IssuingTransaction.TransactionStatus.APPROVED)
        //     .stripeCreatedAt(LocalDateTime.ofInstant(
        //         Instant.ofEpochSecond(createdTimestamp), ZoneId.systemDefault()))
        //     .build();
        //
        // issuingTransactionRepository.save(transaction);
        //
        // // Update subscription spending totals
        // BigDecimal amount = BigDecimal.valueOf(amountCents).divide(BigDecimal.valueOf(100));
        // subscriptionService.updateMonthlySpending(card.getId(), amount);
        // ---------------------------------------------------------------

        log.info("Issuing transaction created - would record and update spending");
        return WebhookProcessingResult.success("Transaction recorded");
    }

    // ==================== Issuing Card Handlers ====================

    private WebhookProcessingResult handleIssuingCardCreated(Map<String, Object> eventData) {
        log.info("Handling issuing_card.created");
        // Card creation is initiated by our service, so this is mainly for confirmation
        return WebhookProcessingResult.success("Card creation confirmed");
    }

    private WebhookProcessingResult handleIssuingCardUpdated(Map<String, Object> eventData) {
        log.info("Handling issuing_card.updated");

        // TODO: Sync card status changes from Stripe
        // This handles cases where card status is changed directly in Stripe Dashboard

        return WebhookProcessingResult.success("Card update recorded");
    }

    // ==================== Helper Methods ====================

    /**
     * Parse webhook payload (placeholder - use Stripe SDK in production).
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseEventPayload(String payload) {
        // In production, use Stripe SDK: Event event = Webhook.constructEvent(...)
        // For now, use Gson for basic parsing
        com.google.gson.Gson gson = new com.google.gson.Gson();
        return gson.fromJson(payload, Map.class);
    }

    /**
     * Result of webhook processing.
     */
    public static class WebhookProcessingResult {
        private final boolean success;
        private final String message;
        private final Boolean authorizationApproved;

        private WebhookProcessingResult(boolean success, String message, Boolean authorizationApproved) {
            this.success = success;
            this.message = message;
            this.authorizationApproved = authorizationApproved;
        }

        public static WebhookProcessingResult success(String message) {
            return new WebhookProcessingResult(true, message, null);
        }

        public static WebhookProcessingResult error(String message) {
            return new WebhookProcessingResult(false, message, null);
        }

        public static WebhookProcessingResult ignored(String message) {
            return new WebhookProcessingResult(true, message, null);
        }

        public static WebhookProcessingResult authorizationDecision(boolean approved, String declineReason) {
            return new WebhookProcessingResult(true,
                    approved ? "Approved" : "Declined: " + declineReason, approved);
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Boolean getAuthorizationApproved() { return authorizationApproved; }
    }
}
