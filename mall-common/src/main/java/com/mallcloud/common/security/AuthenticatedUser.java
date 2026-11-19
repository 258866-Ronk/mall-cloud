package com.mallcloud.common.security;

public record AuthenticatedUser(Long userId, String username, String role) {
    public boolean isAdmin() { return "ADMIN".equalsIgnoreCase(role); }
}
