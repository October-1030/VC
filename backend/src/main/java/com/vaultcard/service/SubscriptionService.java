package com.vaultcard.service;

import com.vaultcard.dto.CreateSubscriptionRequest;
import com.vaultcard.dto.SubscriptionListResponse;
import com.vaultcard.dto.SubscriptionResponse;
import com.vaultcard.entity.IssuingCard;
import com.vaultcard.entity.SubscriptionProfile;
import com.vaultcard.entity.User;
import com.vaultcard.repository.IssuingCardRepository;
import com.vaultcard.repository.IssuingTransactionRepository;
import com.vaultcard.repository.SubscriptionProfileRepository;
import com.vaultcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing subscription profiles and linked virtual cards.
 *
 * Each subscription profile is linked to a dedicated virtual card,
 * allowing users to manage different subscriptions with separate cards and limits.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionProfileRepository subscriptionProfileRepository;
    private final IssuingCardRepository issuingCardRepository;
    private final IssuingTransactionRepository issuingTransactionRepository;
    private final UserRepository userRepository;
    private final IssuingCardService issuingCardService;

    @Value("${vaultcard.max-subscriptions-per-user:10}")
    private int maxSubscriptionsPerUser;

    /**
     * Create a new subscription profile with a linked virtual card.
     *
     * @param userId  The user ID
     * @param request The subscription creation request
     * @return SubscriptionResponse with profile and card info
     */
    @Transactional
    public SubscriptionResponse createSubscription(String userId, CreateSubscriptionRequest request) {
        log.info("Creating subscription '{}' for user: {} with monthly limit: {}",
                request.getNickname(), userId, request.getMonthlyLimit());

        // 1. Validate user exists and is eligible
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (!user.isActive()) {
            throw new IllegalStateException("User account is not active");
        }

        // TODO: Check user KYC status
        // if (user.getKycStatus() != User.KycStatus.VERIFIED) {
        //     throw new IllegalStateException("KYC verification required before creating subscriptions");
        // }

        // 2. Check subscription count limit
        long activeCount = subscriptionProfileRepository.countByUserIdAndStatus(
                userId, SubscriptionProfile.ProfileStatus.ACTIVE);
        if (activeCount >= maxSubscriptionsPerUser) {
            throw new IllegalStateException(
                    "Maximum number of subscriptions reached: " + maxSubscriptionsPerUser);
        }

        // TODO: Check user has sufficient balance for the monthly limit
        // BigDecimal userBalance = userBalanceService.getBalance(userId);
        // if (userBalance.compareTo(request.getMonthlyLimit()) < 0) {
        //     throw new IllegalStateException("Insufficient balance for this monthly limit");
        // }

        // 3. Create virtual card via Stripe Issuing
        // TODO: Call Stripe Issuing API to create card
        // ---------------------------------------------------------------
        // IssuingCard card = issuingCardService.createCard(userId, request.getNickname(),
        //     request.getMonthlyLimit().multiply(BigDecimal.valueOf(100)).longValue());
        // ---------------------------------------------------------------

        // For now, create a placeholder card (Test Mode)
        IssuingCard card = IssuingCard.builder()
                .userId(userId)
                .stripeCardId("ic_test_" + System.currentTimeMillis())
                .last4("4242")
                .brand("visa")
                .expMonth(12)
                .expYear(2028)
                .cardType(IssuingCard.CardType.VIRTUAL)
                .status(IssuingCard.CardStatus.ACTIVE)
                .nickname(request.getNickname())
                .spendingLimitPerMonth(request.getMonthlyLimit().multiply(BigDecimal.valueOf(100)).longValue())
                .build();
        card = issuingCardRepository.save(card);

        // 4. Create subscription profile
        SubscriptionProfile profile = SubscriptionProfile.builder()
                .userId(userId)
                .nickname(request.getNickname())
                .linkedCardId(card.getId())
                .monthlyLimit(request.getMonthlyLimit())
                .currentMonthSpent(BigDecimal.ZERO)
                .status(SubscriptionProfile.ProfileStatus.ACTIVE)
                .allowedMccCodes(request.getAllowedMccCodes())
                .notes(request.getNotes())
                .build();

        profile = subscriptionProfileRepository.save(profile);

        log.info("Created subscription profile: {} with card: {}", profile.getId(), card.getId());

        return mapToResponse(profile, card);
    }

    /**
     * Get all subscriptions for a user.
     */
    public SubscriptionListResponse getUserSubscriptions(String userId) {
        log.debug("Getting subscriptions for user: {}", userId);

        List<SubscriptionProfile> profiles = subscriptionProfileRepository
                .findByUserIdOrderByCreatedAtDesc(userId);

        List<SubscriptionResponse> responses = profiles.stream()
                .map(profile -> {
                    IssuingCard card = profile.getLinkedCardId() != null
                            ? issuingCardRepository.findById(profile.getLinkedCardId()).orElse(null)
                            : null;
                    return mapToResponse(profile, card);
                })
                .collect(Collectors.toList());

        // Calculate summary
        BigDecimal totalLimit = profiles.stream()
                .filter(p -> p.getStatus() == SubscriptionProfile.ProfileStatus.ACTIVE)
                .map(SubscriptionProfile::getMonthlyLimit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSpent = profiles.stream()
                .filter(p -> p.getStatus() == SubscriptionProfile.ProfileStatus.ACTIVE)
                .map(SubscriptionProfile::getCurrentMonthSpent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int activeCount = (int) profiles.stream()
                .filter(p -> p.getStatus() == SubscriptionProfile.ProfileStatus.ACTIVE)
                .count();

        return SubscriptionListResponse.builder()
                .subscriptions(responses)
                .summary(SubscriptionListResponse.Summary.builder()
                        .totalActive(activeCount)
                        .totalMonthlyLimit(totalLimit)
                        .totalSpentThisMonth(totalSpent)
                        .totalRemaining(totalLimit.subtract(totalSpent))
                        .build())
                .build();
    }

    /**
     * Get a specific subscription by ID.
     */
    public SubscriptionResponse getSubscription(String subscriptionId) {
        SubscriptionProfile profile = subscriptionProfileRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));

        IssuingCard card = profile.getLinkedCardId() != null
                ? issuingCardRepository.findById(profile.getLinkedCardId()).orElse(null)
                : null;

        return mapToResponse(profile, card);
    }

    /**
     * Pause a subscription (freeze the linked card).
     */
    @Transactional
    public SubscriptionResponse pauseSubscription(String subscriptionId) {
        log.info("Pausing subscription: {}", subscriptionId);

        SubscriptionProfile profile = subscriptionProfileRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));

        // TODO: Call Stripe Issuing API to freeze card
        // issuingCardService.freezeCard(profile.getLinkedCardId());

        profile.setStatus(SubscriptionProfile.ProfileStatus.PAUSED);
        profile = subscriptionProfileRepository.save(profile);

        // Update card status
        if (profile.getLinkedCardId() != null) {
            IssuingCard card = issuingCardRepository.findById(profile.getLinkedCardId()).orElse(null);
            if (card != null) {
                card.setStatus(IssuingCard.CardStatus.FROZEN);
                issuingCardRepository.save(card);
            }
        }

        return mapToResponse(profile, null);
    }

    /**
     * Resume a paused subscription.
     */
    @Transactional
    public SubscriptionResponse resumeSubscription(String subscriptionId) {
        log.info("Resuming subscription: {}", subscriptionId);

        SubscriptionProfile profile = subscriptionProfileRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found: " + subscriptionId));

        if (profile.getStatus() != SubscriptionProfile.ProfileStatus.PAUSED) {
            throw new IllegalStateException("Subscription is not paused");
        }

        // TODO: Call Stripe Issuing API to unfreeze card
        // issuingCardService.unfreezeCard(profile.getLinkedCardId());

        profile.setStatus(SubscriptionProfile.ProfileStatus.ACTIVE);
        profile = subscriptionProfileRepository.save(profile);

        // Update card status
        if (profile.getLinkedCardId() != null) {
            IssuingCard card = issuingCardRepository.findById(profile.getLinkedCardId()).orElse(null);
            if (card != null) {
                card.setStatus(IssuingCard.CardStatus.ACTIVE);
                issuingCardRepository.save(card);
            }
        }

        return mapToResponse(profile, null);
    }

    /**
     * Update monthly spending total for a subscription (called when transactions occur).
     */
    @Transactional
    public void updateMonthlySpending(String cardId, BigDecimal transactionAmount) {
        subscriptionProfileRepository.findByLinkedCardId(cardId)
                .ifPresent(profile -> {
                    BigDecimal newTotal = profile.getCurrentMonthSpent().add(transactionAmount);
                    profile.setCurrentMonthSpent(newTotal);
                    subscriptionProfileRepository.save(profile);
                    log.debug("Updated monthly spending for subscription {}: {}", profile.getId(), newTotal);
                });
    }

    /**
     * Reset monthly spending for all subscriptions (called by scheduled job on month start).
     */
    @Transactional
    public void resetMonthlySpending() {
        log.info("Resetting monthly spending for all active subscriptions");
        List<SubscriptionProfile> activeProfiles = subscriptionProfileRepository
                .findByUserIdAndStatus(null, SubscriptionProfile.ProfileStatus.ACTIVE);

        // Actually we need a different query - get all active regardless of user
        // For simplicity, using findAll and filtering
        subscriptionProfileRepository.findAll().stream()
                .filter(p -> p.getStatus() == SubscriptionProfile.ProfileStatus.ACTIVE)
                .forEach(profile -> {
                    profile.setCurrentMonthSpent(BigDecimal.ZERO);
                    subscriptionProfileRepository.save(profile);
                });
    }

    /**
     * Map entity to response DTO.
     */
    private SubscriptionResponse mapToResponse(SubscriptionProfile profile, IssuingCard card) {
        SubscriptionResponse.CardSummary cardSummary = null;
        if (card != null) {
            cardSummary = SubscriptionResponse.CardSummary.builder()
                    .cardId(card.getId())
                    .last4(card.getLast4())
                    .brand(card.getBrand())
                    .expiration(card.getExpMonth() + "/" + (card.getExpYear() % 100))
                    .status(card.getStatus().name())
                    .build();
        }

        BigDecimal remaining = profile.getMonthlyLimit().subtract(profile.getCurrentMonthSpent());

        return SubscriptionResponse.builder()
                .id(profile.getId())
                .nickname(profile.getNickname())
                .monthlyLimit(profile.getMonthlyLimit())
                .currentMonthSpent(profile.getCurrentMonthSpent())
                .remainingBudget(remaining.compareTo(BigDecimal.ZERO) > 0 ? remaining : BigDecimal.ZERO)
                .status(profile.getStatus().name())
                .linkedCard(cardSummary)
                .createdAt(profile.getCreatedAt())
                .notes(profile.getNotes())
                .build();
    }
}
