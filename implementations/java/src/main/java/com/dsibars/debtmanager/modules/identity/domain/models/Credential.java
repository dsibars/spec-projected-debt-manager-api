package com.dsibars.debtmanager.modules.identity.domain.models;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record Credential(
        UUID id,
        UUID userId,
        Provider provider,
        String secret,
        Map<String, Object> providerData,
        OffsetDateTime createdAt
) {
    public enum Provider {
        LOCAL, GOOGLE, APPLE
    }
}
