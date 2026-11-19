package com.mallcloud.user.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(@NotBlank @Size(min=3,max=32) String username, @NotBlank @Size(min=8,max=64) String password,
                              @NotBlank @Pattern(regexp="^1[3-9]\\d{9}$") String phone, @Email String email) {}
