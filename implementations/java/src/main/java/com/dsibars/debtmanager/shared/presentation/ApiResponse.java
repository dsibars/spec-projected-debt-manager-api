package com.dsibars.debtmanager.shared.presentation;

public record ApiResponse<T>(T data, Object meta) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null);
    }

    public static <T> ApiResponse<T> success(T data, Object meta) {
        return new ApiResponse<>(data, meta);
    }
}
