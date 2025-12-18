package com.vaultcard.repository;

import com.vaultcard.entity.SubscriptionProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for SubscriptionProfile entity.
 */
@Repository
public interface SubscriptionProfileRepository extends JpaRepository<SubscriptionProfile, String> {

    /**
     * Find all subscription profiles for a user
     */
    List<SubscriptionProfile> findByUserIdOrderByCreatedAtDesc(String userId);

    /**
     * Find all active subscription profiles for a user
     */
    List<SubscriptionProfile> findByUserIdAndStatus(String userId, SubscriptionProfile.ProfileStatus status);

    /**
     * Find subscription profile by linked card ID
     */
    Optional<SubscriptionProfile> findByLinkedCardId(String linkedCardId);

    /**
     * Count active profiles for a user (for limit checking)
     */
    long countByUserIdAndStatus(String userId, SubscriptionProfile.ProfileStatus status);
}
