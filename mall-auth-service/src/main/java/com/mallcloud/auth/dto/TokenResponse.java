package com.mallcloud.auth.dto;

public record TokenResponse(String accessToken, String tokenType, long expiresIn, Long userId, String username, String role) {}
