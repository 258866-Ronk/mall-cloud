package com.mallcloud.product.controller;

import com.mallcloud.common.api.ApiResponse; import com.mallcloud.common.security.UserContext; import com.mallcloud.product.dto.CategoryRequest;
import com.mallcloud.product.entity.CategoryEntity; import com.mallcloud.product.service.CategoryService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service; public CategoryController(CategoryService service) { this.service = service; }
    @GetMapping public ApiResponse<List<CategoryEntity>> list() { return ApiResponse.success(service.list()); }
    @PostMapping("/admin") public ApiResponse<CategoryEntity> create(@Valid @RequestBody CategoryRequest request) { UserContext.requireAdmin(); return ApiResponse.success(service.create(request)); }
    @PutMapping("/admin/{id}") public ApiResponse<CategoryEntity> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) { UserContext.requireAdmin(); return ApiResponse.success(service.update(id, request)); }
}
