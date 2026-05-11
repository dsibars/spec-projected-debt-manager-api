package com.dsibars.debtmanager;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public abstract class BaseIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgresWrite = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("spd_debt_manager")
            .withUsername("spd_user")
            .withPassword("spd_pass");

    @Container
    static PostgreSQLContainer<?> postgresRead = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("spd_debt_manager")
            .withUsername("spd_user")
            .withPassword("spd_pass");

    @Container
    static RabbitMQContainer rabbitmq = new RabbitMQContainer("rabbitmq:3-management-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.write.url", postgresWrite::getJdbcUrl);
        registry.add("spring.datasource.write.username", postgresWrite::getUsername);
        registry.add("spring.datasource.write.password", postgresWrite::getPassword);

        registry.add("spring.datasource.read.url", postgresRead::getJdbcUrl);
        registry.add("spring.datasource.read.username", postgresRead::getUsername);
        registry.add("spring.datasource.read.password", postgresRead::getPassword);

        registry.add("spring.rabbitmq.host", rabbitmq::getHost);
        registry.add("spring.rabbitmq.port", rabbitmq::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitmq::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitmq::getAdminPassword);
    }
}
