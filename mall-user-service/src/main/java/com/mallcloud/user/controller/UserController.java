package com.mallcloud.user.controller;

import com.mallcloud.api.user.*; import com.mallcloud.common.api.*; import com.mallcloud.common.security.UserContext;
import com.mallcloud.user.dto.RegisterRequest; import com.mallcloud.user.service.UserService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/users")
public class UserController {
    private final UserService service; public UserController(UserService service) { this.service = service; }
    @PostMapping("/register") public ApiResponse<UserSummary> register(@Valid @RequestBody RegisterRequest request) { return ApiResponse.success(service.register(request)); }
    @GetMapping("/me") public ApiResponse<UserSummary> me() { return ApiResponse.success(service.get(UserContext.require().userId())); }
    @GetMapping("/admin") public ApiResponse<PageResponse<UserSummary>> page(@RequestParam(defaultValue="1") long page, @RequestParam(defaultValue="20") long size) { UserContext.requireAdmin(); return ApiResponse.success(service.page(page, size)); }
    @PutMapping("/admin/{id}/status") public ApiResponse<UserSummary> status(@PathVariable Long id, @RequestParam int status) { UserContext.requireAdmin(); return ApiResponse.success(service.updateStatus(id, status)); }
}
