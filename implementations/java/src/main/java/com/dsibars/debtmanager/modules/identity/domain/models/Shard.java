package com.dsibars.debtmanager.modules.identity.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Shard(
        UUID id,
        String name,
        Status status,
        int capacity,
        int currentLoad,
        String region,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public enum Status {
        ACTIVE, FULL, MAINTENANCE, DORMANT
    }
}
