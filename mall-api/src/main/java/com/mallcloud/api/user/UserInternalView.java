package com.mallcloud.api.user;

public record UserInternalView(Long id, String username, String passwordHash, String phone, String email, Integer status, String role) {}
