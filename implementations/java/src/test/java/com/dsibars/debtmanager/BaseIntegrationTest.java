package com.dsibars.debtmanager;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Base class for integration tests.
 * Currently using H2 for local skeleton phase due to environment restrictions.
 * To enable Testcontainers, uncomment the @Testcontainers and @Container annotations
 * and ensure a valid Docker environment is available.
 */
public abstract class BaseIntegrationTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // H2 configuration for tests
        registry.add("spring.datasource.write.jdbc-url", () -> "jdbc:h2:mem:debtmanager_write;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        registry.add("spring.datasource.write.username", () -> "sa");
        registry.add("spring.datasource.write.password", () -> "");

        registry.add("spring.datasource.read.jdbc-url", () -> "jdbc:h2:mem:debtmanager_read;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
        registry.add("spring.datasource.read.username", () -> "sa");
        registry.add("spring.datasource.read.password", () -> "");

        // Mock RabbitMQ for tests if needed, or use a real container
    }
}
