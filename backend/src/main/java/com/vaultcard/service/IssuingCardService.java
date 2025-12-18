package com.vaultcard.service;

import com.vaultcard.entity.IssuingCard;
import com.vaultcard.entity.User;
import com.vaultcard.repository.IssuingCardRepository;
import com.vaultcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for managing Stripe Issuing virtual cards.
 *
 * IMPORTANT: This service never stores or logs sensitive card data (full PAN, CVC).
 * All card operations go through Stripe Issuing API.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IssuingCardService {

    private final IssuingCardRepository issuingCardRepository;
    private final UserRepository userRepository;

    @Value("${stripe.secret-key:}")
    private String stripeSecretKey;

    @Value("${vaultcard.max-cards-per-user:20}")
    private int maxCardsPerUser;

    /**
     * Create a new virtual card via Stripe Issuing.
     *
     * @param userId           User ID
     * @param nickname         Card nickname
     * @param monthlyLimitCents Monthly spending limit in cents
     * @return Created IssuingCard entity
     */
    @Transactional
    public IssuingCard createCard(String userId, String nickname, Long monthlyLimitCents) {
        log.info("Creating virtual card for user: {} with nickname: {}", userId, nickname);

        // 1. Validate user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (!user.isActive()) {
            throw new IllegalStateException("User account is not active");
        }

        // 2. Check card count limit
        long cardCount = issuingCardRepository.countByUserIdAndStatusNot(userId, IssuingCard.CardStatus.CANCELED);
        if (cardCount >= maxCardsPerUser) {
            throw new IllegalStateException("Maximum number of cards reached: " + maxCardsPerUser);
        }

        // 3. Ensure user has a Stripe Cardholder
        // TODO: Create Stripe Cardholder if not exists
        // ---------------------------------------------------------------
        // if (user.getStripeCardholderId() == null) {
        //     Stripe.apiKey = stripeSecretKey;
        //
        //     CardholderCreateParams params = CardholderCreateParams.builder()
        //         .setName(user.getName())
        //         .setEmail(user.getEmail())
        //         .setType(CardholderCreateParams.Type.INDIVIDUAL)
        //         .setStatus(CardholderCreateParams.Status.ACTIVE)
        //         .build();
        //
        //     Cardholder cardholder = Cardholder.create(params);
        //     user.setStripeCardholderId(cardholder.getId());
        //     userRepository.save(user);
        // }
        // ---------------------------------------------------------------

        // 4. Create Stripe Issuing Card
        // TODO: Call Stripe Issuing API to create card in test mode
        // ---------------------------------------------------------------
        // Stripe.apiKey = stripeSecretKey;
        //
        // CardCreateParams.SpendingControls spendingControls = CardCreateParams.SpendingControls.builder()
        //     .addSpendingLimit(CardCreateParams.SpendingControls.SpendingLimit.builder()
        //         .setAmount(monthlyLimitCents)
        //         .setInterval(CardCreateParams.SpendingControls.SpendingLimit.Interval.PER_MONTH)
        //         .build())
        //     .build();
        //
        // CardCreateParams params = CardCreateParams.builder()
        //     .setCardholder(user.getStripeCardholderId())
        //     .setType(CardCreateParams.Type.VIRTUAL)
        //     .setCurrency("usd")
        //     .setStatus(CardCreateParams.Status.ACTIVE)
        //     .setSpendingControls(spendingControls)
        //     .putMetadata("user_id", userId)
        //     .putMetadata("nickname", nickname)
        //     .build();
        //
        // Card stripeCard = Card.create(params);
        //
        // IssuingCard card = IssuingCard.builder()
        //     .userId(userId)
        //     .stripeCardId(stripeCard.getId())
        //     .last4(stripeCard.getLast4())
        //     .brand(stripeCard.getBrand())
        //     .expMonth(stripeCard.getExpMonth().intValue())
        //     .expYear(stripeCard.getExpYear().intValue())
        //     .cardType(IssuingCard.CardType.VIRTUAL)
        //     .status(IssuingCard.CardStatus.ACTIVE)
        //     .nickname(nickname)
        //     .spendingLimitPerMonth(monthlyLimitCents)
        //     .build();
        // ---------------------------------------------------------------

        // For now, create placeholder (Test Mode)
        IssuingCard card = IssuingCard.builder()
                .userId(userId)
                .stripeCardId("ic_test_" + System.currentTimeMillis())
                .last4("4242")
                .brand("visa")
                .expMonth(12)
                .expYear(2028)
                .cardType(IssuingCard.CardType.VIRTUAL)
                .status(IssuingCard.CardStatus.ACTIVE)
                .nickname(nickname)
                .spendingLimitPerMonth(monthlyLimitCents)
                .build();

        card = issuingCardRepository.save(card);
        log.info("Created virtual card: {} for user: {}", card.getId(), userId);

        return card;
    }

    /**
     * Get card by ID.
     */
    public IssuingCard getCard(String cardId) {
        return issuingCardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found: " + cardId));
    }

    /**
     * Get card by Stripe Card ID.
     */
    public IssuingCard getCardByStripeId(String stripeCardId) {
        return issuingCardRepository.findByStripeCardId(stripeCardId)
                .orElseThrow(() -> new IllegalArgumentException("Card not found for Stripe ID: " + stripeCardId));
    }

    /**
     * Get all cards for a user.
     */
    public List<IssuingCard> getUserCards(String userId) {
        return issuingCardRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Freeze a card.
     */
    @Transactional
    public IssuingCard freezeCard(String cardId) {
        log.info("Freezing card: {}", cardId);

        IssuingCard card = getCard(cardId);

        if (card.getStatus() == IssuingCard.CardStatus.CANCELED) {
            throw new IllegalStateException("Cannot freeze a canceled card");
        }

        // TODO: Call Stripe Issuing API to update card status
        // ---------------------------------------------------------------
        // Stripe.apiKey = stripeSecretKey;
        // Card stripeCard = Card.retrieve(card.getStripeCardId());
        // CardUpdateParams params = CardUpdateParams.builder()
        //     .setStatus(CardUpdateParams.Status.INACTIVE)
        //     .build();
        // stripeCard.update(params);
        // ---------------------------------------------------------------

        card.setStatus(IssuingCard.CardStatus.FROZEN);
        return issuingCardRepository.save(card);
    }

    /**
     * Unfreeze a card.
     */
    @Transactional
    public IssuingCard unfreezeCard(String cardId) {
        log.info("Unfreezing card: {}", cardId);

        IssuingCard card = getCard(cardId);

        if (card.getStatus() != IssuingCard.CardStatus.FROZEN) {
            throw new IllegalStateException("Card is not frozen");
        }

        // TODO: Call Stripe Issuing API to update card status
        // ---------------------------------------------------------------
        // Stripe.apiKey = stripeSecretKey;
        // Card stripeCard = Card.retrieve(card.getStripeCardId());
        // CardUpdateParams params = CardUpdateParams.builder()
        //     .setStatus(CardUpdateParams.Status.ACTIVE)
        //     .build();
        // stripeCard.update(params);
        // ---------------------------------------------------------------

        card.setStatus(IssuingCard.CardStatus.ACTIVE);
        return issuingCardRepository.save(card);
    }

    /**
     * Cancel a card permanently.
     */
    @Transactional
    public IssuingCard cancelCard(String cardId) {
        log.info("Canceling card: {}", cardId);

        IssuingCard card = getCard(cardId);

        if (card.getStatus() == IssuingCard.CardStatus.CANCELED) {
            throw new IllegalStateException("Card is already canceled");
        }

        // TODO: Call Stripe Issuing API to cancel card
        // ---------------------------------------------------------------
        // Stripe.apiKey = stripeSecretKey;
        // Card stripeCard = Card.retrieve(card.getStripeCardId());
        // CardUpdateParams params = CardUpdateParams.builder()
        //     .setStatus(CardUpdateParams.Status.CANCELED)
        //     .build();
        // stripeCard.update(params);
        // ---------------------------------------------------------------

        card.setStatus(IssuingCard.CardStatus.CANCELED);
        card.setCanceledAt(java.time.LocalDateTime.now());
        return issuingCardRepository.save(card);
    }

    /**
     * Update card spending limits.
     */
    @Transactional
    public IssuingCard updateSpendingLimits(String cardId, Long perTransactionCents, Long perMonthCents) {
        log.info("Updating spending limits for card: {}", cardId);

        IssuingCard card = getCard(cardId);

        // TODO: Call Stripe Issuing API to update spending controls
        // ---------------------------------------------------------------
        // Stripe.apiKey = stripeSecretKey;
        // Card stripeCard = Card.retrieve(card.getStripeCardId());
        //
        // List<CardUpdateParams.SpendingControls.SpendingLimit> limits = new ArrayList<>();
        // if (perTransactionCents != null) {
        //     limits.add(CardUpdateParams.SpendingControls.SpendingLimit.builder()
        //         .setAmount(perTransactionCents)
        //         .setInterval(CardUpdateParams.SpendingControls.SpendingLimit.Interval.PER_TRANSACTION)
        //         .build());
        // }
        // if (perMonthCents != null) {
        //     limits.add(CardUpdateParams.SpendingControls.SpendingLimit.builder()
        //         .setAmount(perMonthCents)
        //         .setInterval(CardUpdateParams.SpendingControls.SpendingLimit.Interval.PER_MONTH)
        //         .build());
        // }
        //
        // CardUpdateParams params = CardUpdateParams.builder()
        //     .setSpendingControls(CardUpdateParams.SpendingControls.builder()
        //         .setSpendingLimits(limits)
        //         .build())
        //     .build();
        // stripeCard.update(params);
        // ---------------------------------------------------------------

        if (perTransactionCents != null) {
            card.setSpendingLimitPerTransaction(perTransactionCents);
        }
        if (perMonthCents != null) {
            card.setSpendingLimitPerMonth(perMonthCents);
        }

        return issuingCardRepository.save(card);
    }
}
