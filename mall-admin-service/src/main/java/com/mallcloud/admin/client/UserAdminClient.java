package com.mallcloud.admin.client;
import com.mallcloud.api.user.UserSummary; import com.mallcloud.common.api.*; import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="mall-user-service",contextId="userAdmin") public interface UserAdminClient {@GetMapping("/api/users/admin")ApiResponse<PageResponse<UserSummary>> page(@RequestParam("page")long page,@RequestParam("size")long size);@PutMapping("/api/users/admin/{id}/status")ApiResponse<UserSummary> status(@PathVariable("id")Long id,@RequestParam("status")int status);}
