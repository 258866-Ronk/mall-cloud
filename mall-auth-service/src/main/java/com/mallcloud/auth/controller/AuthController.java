package com.mallcloud.auth.controller;

import com.mallcloud.auth.dto.LoginRequest; import com.mallcloud.auth.dto.TokenResponse; import com.mallcloud.auth.service.AuthService;
import com.mallcloud.common.api.ApiResponse; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService; public AuthController(AuthService authService) { this.authService = authService; }
    @PostMapping("/login") public ApiResponse<TokenResponse> login(@Valid @RequestBody LoginRequest request) { return ApiResponse.success(authService.login(request)); }
}
