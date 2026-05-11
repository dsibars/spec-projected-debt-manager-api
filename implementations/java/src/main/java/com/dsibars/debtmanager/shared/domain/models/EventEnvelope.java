package com.dsibars.debtmanager.shared.domain.models;

import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T>(
        UUID eventId,
        Instant occurredAt,
        String eventType,
        Metadata metadata,
        T payload
) {
    public record Metadata(
            UUID tenantId,
            UUID userId,
            UUID correlationId
    ) {}
}
