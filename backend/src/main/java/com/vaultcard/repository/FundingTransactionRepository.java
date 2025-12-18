package com.vaultcard.repository;

import com.vaultcard.entity.FundingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository for FundingTransaction entity.
 */
@Repository
public interface FundingTransactionRepository extends JpaRepository<FundingTransaction, String> {

    /**
     * Find all funding transactions for a user, ordered by creation date desc
     */
    List<FundingTransaction> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find funding transaction by Stripe Payment ID
     */
    Optional<FundingTransaction> findByStripePaymentId(String stripePaymentId);

    /**
     * Find all transactions with a specific status
     */
    List<FundingTransaction> findByUserIdAndStatus(String userId, FundingTransaction.FundingStatus status);

    /**
     * Calculate total successful funding amount for a user
     */
    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FundingTransaction f " +
           "WHERE f.userId = :userId AND f.status = 'SUCCEEDED'")
    BigDecimal getTotalFundedAmount(@Param("userId") String userId);
}
