package com.vaultcard.repository;

import com.vaultcard.entity.IssuingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for IssuingTransaction entity.
 */
@Repository
public interface IssuingTransactionRepository extends JpaRepository<IssuingTransaction, String> {

    /**
     * Find all transactions for a user
     */
    List<IssuingTransaction> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find transaction by Stripe Transaction ID
     */
    Optional<IssuingTransaction> findByStripeTransactionId(String stripeTransactionId);

    /**
     * Find all transactions for a specific card
     */
    List<IssuingTransaction> findByStripeCardIdOrderByCreatedAtDesc(String stripeCardId);

    /**
     * Find transactions within a date range for a card
     */
    List<IssuingTransaction> findByStripeCardIdAndCreatedAtBetween(
            String stripeCardId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    /**
     * Calculate total spending for a card within a date range
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM IssuingTransaction t " +
           "WHERE t.stripeCardId = :cardId " +
           "AND t.status = 'APPROVED' " +
           "AND t.transactionType = 'PURCHASE' " +
           "AND t.createdAt >= :startDate " +
           "AND t.createdAt <= :endDate")
    BigDecimal getTotalSpendingForPeriod(
            @Param("cardId") String cardId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    /**
     * Calculate total spending for a user (all cards)
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM IssuingTransaction t " +
           "WHERE t.userId = :userId " +
           "AND t.status = 'APPROVED' " +
           "AND t.transactionType = 'PURCHASE'")
    BigDecimal getTotalUserSpending(@Param("userId") String userId);
}
