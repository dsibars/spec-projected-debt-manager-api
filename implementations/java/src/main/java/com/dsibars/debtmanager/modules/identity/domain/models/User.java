package com.dsibars.debtmanager.modules.identity.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public record User(
        UUID id,
        UUID shardId,
        String email,
        boolean isActive,
        OffsetDateTime lastLoginAt,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
