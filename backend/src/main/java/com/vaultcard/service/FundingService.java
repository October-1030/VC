package com.vaultcard.service;

import com.vaultcard.dto.CreateFundingRequest;
import com.vaultcard.dto.FundingResponse;
import com.vaultcard.entity.FundingTransaction;
import com.vaultcard.entity.User;
import com.vaultcard.repository.FundingTransactionRepository;
import com.vaultcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service for handling funding/top-up transactions.
 *
 * This service manages user deposits into VaultCard via Stripe Payments.
 * Supports multiple payment methods: card, Alipay, WeChat Pay (via Stripe).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FundingService {

    private final FundingTransactionRepository fundingTransactionRepository;
    private final UserRepository userRepository;

    @Value("${stripe.secret-key:}")
    private String stripeSecretKey;

    /**
     * Create a new funding transaction and initiate Stripe payment.
     *
     * @param userId  The user ID
     * @param request The funding request
     * @return FundingResponse with client secret for frontend payment confirmation
     */
    @Transactional
    public FundingResponse createFunding(String userId, CreateFundingRequest request) {
        log.info("Creating funding transaction for user: {}, amount: {} {}",
                userId, request.getAmount(), request.getCurrency());

        // 1. Validate user exists and is active
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (!user.isActive()) {
            throw new IllegalStateException("User account is not active");
        }

        // TODO: Check user KYC status before allowing funding
        // if (user.getKycStatus() != User.KycStatus.VERIFIED) {
        //     throw new IllegalStateException("KYC verification required before funding");
        // }

        // 2. Create FundingTransaction record (PENDING status)
        FundingTransaction transaction = FundingTransaction.builder()
                .userId(userId)
                .amount(request.getAmount())
                .currency(request.getCurrency().toLowerCase())
                .paymentMethodType(request.getPaymentMethodType())
                .status(FundingTransaction.FundingStatus.PENDING)
                .build();

        transaction = fundingTransactionRepository.save(transaction);

        // 3. Create Stripe PaymentIntent
        // TODO: Integrate Stripe Payments API to create PaymentIntent
        // ---------------------------------------------------------------
        // Stripe.apiKey = stripeSecretKey;
        //
        // PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
        //     .setAmount(request.getAmount().multiply(BigDecimal.valueOf(100)).longValue()) // Convert to cents
        //     .setCurrency(request.getCurrency())
        //     .setCustomer(user.getStripeCustomerId())
        //     .addPaymentMethodType("card")
        //     .addPaymentMethodType("alipay")  // If enabled
        //     .putMetadata("funding_transaction_id", transaction.getId())
        //     .putMetadata("user_id", userId)
        //     .build();
        //
        // PaymentIntent paymentIntent = PaymentIntent.create(params);
        //
        // transaction.setStripePaymentId(paymentIntent.getId());
        // transaction.setStripeClientSecret(paymentIntent.getClientSecret());
        // fundingTransactionRepository.save(transaction);
        // ---------------------------------------------------------------

        // For now, return mock response (Test Mode placeholder)
        String mockClientSecret = "pi_test_" + transaction.getId() + "_secret_test";
        String mockPaymentId = "pi_test_" + System.currentTimeMillis();

        transaction.setStripePaymentId(mockPaymentId);
        transaction.setStripeClientSecret(mockClientSecret);
        fundingTransactionRepository.save(transaction);

        log.info("Created funding transaction: {} with Stripe PaymentIntent: {}",
                transaction.getId(), mockPaymentId);

        return FundingResponse.builder()
                .transactionId(transaction.getId())
                .stripePaymentId(mockPaymentId)
                .clientSecret(mockClientSecret)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .status(transaction.getStatus().name())
                .createdAt(transaction.getCreatedAt())
                .build();
    }

    /**
     * Get funding transaction by ID.
     */
    public FundingTransaction getFundingTransaction(String transactionId) {
        return fundingTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + transactionId));
    }

    /**
     * Get all funding transactions for a user.
     */
    public List<FundingTransaction> getUserFundingTransactions(String userId) {
        return fundingTransactionRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Get total funded amount for a user.
     */
    public BigDecimal getTotalFundedAmount(String userId) {
        return fundingTransactionRepository.getTotalFundedAmount(userId);
    }

    /**
     * Update funding transaction status (called from webhook).
     *
     * @param stripePaymentId Stripe PaymentIntent ID
     * @param newStatus       New status
     */
    @Transactional
    public void updateFundingStatus(String stripePaymentId, FundingTransaction.FundingStatus newStatus) {
        log.info("Updating funding status for PaymentIntent: {} to {}", stripePaymentId, newStatus);

        FundingTransaction transaction = fundingTransactionRepository
                .findByStripePaymentId(stripePaymentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Funding transaction not found for PaymentIntent: " + stripePaymentId));

        transaction.setStatus(newStatus);

        if (newStatus == FundingTransaction.FundingStatus.SUCCEEDED) {
            transaction.setCompletedAt(java.time.LocalDateTime.now());

            // TODO: Credit user's internal balance
            // userBalanceService.credit(transaction.getUserId(), transaction.getAmount());
        }

        fundingTransactionRepository.save(transaction);
        log.info("Updated funding transaction {} to status {}", transaction.getId(), newStatus);
    }
}
