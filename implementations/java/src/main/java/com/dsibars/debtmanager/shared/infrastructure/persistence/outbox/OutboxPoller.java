package com.dsibars.debtmanager.shared.infrastructure.persistence.outbox;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class OutboxPoller {
    private final OutboxRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;

    public OutboxPoller(OutboxRepository outboxRepository, RabbitTemplate rabbitTemplate) {
        this.outboxRepository = outboxRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void poll() {
        List<OutboxEvent> pendingEvents = outboxRepository.findByProcessedAtIsNullOrderByOccurredAtAsc();
        for (OutboxEvent event : pendingEvents) {
            try {
                String routingKey = String.format("%s.%s", event.getTenantId(), event.getEventType());
                // In a real scenario, we would parse and wrap in EventEnvelope if not already
                rabbitTemplate.convertAndSend("spd.domain.events", routingKey, event.getPayload());

                event.setProcessedAt(OffsetDateTime.now());
                outboxRepository.save(event);
            } catch (Exception e) {
                event.setRetryCount(event.getRetryCount() + 1);
                outboxRepository.save(event);
            }
        }
    }
}
