package com.vaultcard.repository;

import com.vaultcard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user by email address
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by Stripe Customer ID
     */
    Optional<User> findByStripeCustomerId(String stripeCustomerId);

    /**
     * Find user by Stripe Cardholder ID
     */
    Optional<User> findByStripeCardholderId(String stripeCardholderId);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
}
