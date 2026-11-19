package com.mallcloud.common.api;

import java.time.Instant;

public record ApiResponse<T>(int code, String message, T data, long timestamp) {
    public static <T> ApiResponse<T> success(T data) { return new ApiResponse<>(200, "success", data, Instant.now().toEpochMilli()); }
    public static ApiResponse<Void> success() { return success(null); }
    public static <T> ApiResponse<T> failure(int code, String message) { return new ApiResponse<>(code, message, null, Instant.now().toEpochMilli()); }
}
