package com.vaultcard.config;

import com.vaultcard.entity.User;
import com.vaultcard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Initializes test data for development environment.
 *
 * Creates a default test user for API testing without authentication.
 * Only runs in non-production profiles.
 */
@Component
@Profile("!prod")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        log.info("Initializing test data...");

        // Create default test user if not exists
        if (!userRepository.existsByEmail("test@vaultcard.dev")) {
            User testUser = User.builder()
                    .id("test-user-001")  // Fixed ID for easy testing
                    .email("test@vaultcard.dev")
                    .name("Test Developer")
                    .country("US")
                    .kycStatus(User.KycStatus.VERIFIED)
                    .active(true)
                    .build();

            // Need to use a special save that allows setting the ID
            // Since we're using UUID generation, we'll let it auto-generate
            // and just create with specific email
            testUser = userRepository.save(testUser);
            log.info("Created test user: {} ({})", testUser.getEmail(), testUser.getId());
        } else {
            log.info("Test user already exists");
        }

        log.info("Test data initialization complete");
        log.info("===========================================");
        log.info("VaultCard Backend Started (Test Mode)");
        log.info("===========================================");
        log.info("API Endpoints:");
        log.info("  POST /api/funding          - Create funding transaction");
        log.info("  GET  /api/funding          - List funding transactions");
        log.info("  POST /api/subscriptions    - Create subscription");
        log.info("  GET  /api/subscriptions    - List subscriptions");
        log.info("  POST /api/webhooks/stripe  - Stripe webhook endpoint");
        log.info("");
        log.info("H2 Console: http://localhost:8080/h2-console");
        log.info("  JDBC URL: jdbc:h2:file:./data/vaultcard");
        log.info("  User: sa, Password: (empty)");
        log.info("===========================================");
    }
}
