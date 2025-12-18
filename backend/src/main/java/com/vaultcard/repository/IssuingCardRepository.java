package com.vaultcard.repository;

import com.vaultcard.entity.IssuingCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for IssuingCard entity.
 */
@Repository
public interface IssuingCardRepository extends JpaRepository<IssuingCard, String> {

    /**
     * Find all cards for a user
     */
    List<IssuingCard> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find card by Stripe Card ID
     */
    Optional<IssuingCard> findByStripeCardId(String stripeCardId);

    /**
     * Find all active cards for a user
     */
    List<IssuingCard> findByUserIdAndStatus(String userId, IssuingCard.CardStatus status);

    /**
     * Count active cards for a user (for limit checking)
     */
    long countByUserIdAndStatusNot(String userId, IssuingCard.CardStatus status);
}
