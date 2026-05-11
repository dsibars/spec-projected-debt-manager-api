package com.dsibars.debtmanager.shared.infrastructure.security;

import java.util.UUID;

public class SecurityContext {
    private static final ThreadLocal<SecurityContextData> CONTEXT = new ThreadLocal<>();

    public record SecurityContextData(UUID userId, UUID tenantId) {}

    public static void set(UUID userId, UUID tenantId) {
        CONTEXT.set(new SecurityContextData(userId, tenantId));
    }

    public static SecurityContextData get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
