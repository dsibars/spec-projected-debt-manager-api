package com.dsibars.debtmanager.shared.domain.models;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email {
        Objects.requireNonNull(value, "Email value cannot be null");
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value.toLowerCase(), email.value.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }
}
