package com.dsibars.debtmanager.shared.infrastructure.messaging;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange domainEventsExchange() {
        return new TopicExchange("spd.domain.events");
    }
}
