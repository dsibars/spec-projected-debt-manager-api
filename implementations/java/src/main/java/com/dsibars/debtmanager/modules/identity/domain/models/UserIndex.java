package com.dsibars.debtmanager.modules.identity.domain.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UserIndex(
        String email,
        UUID userId,
        UUID shardId,
        OffsetDateTime createdAt
) {}
