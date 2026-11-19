package com.mallcloud.api.user;

import java.time.LocalDateTime;

public record UserSummary(Long id, String username, String phone, String email, Integer status, String role, LocalDateTime createTime) {}
