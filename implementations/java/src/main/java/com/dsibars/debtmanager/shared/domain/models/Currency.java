package com.dsibars.debtmanager.shared.domain.models;

import java.util.Objects;
import java.util.regex.Pattern;

public record Currency(String code) {
    private static final Pattern PATTERN = Pattern.compile("^[A-Z]{3}$");

    public Currency {
        Objects.requireNonNull(code, "Currency code cannot be null");
        if (!PATTERN.matcher(code).matches()) {
            throw new IllegalArgumentException("Invalid currency code format");
        }
        // In a real scenario, we would also validate against a list of ISO 4217 codes.
    }
}
