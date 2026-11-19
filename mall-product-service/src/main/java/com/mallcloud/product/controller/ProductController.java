package com.mallcloud.product.controller;

import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.common.api.*; import com.mallcloud.common.security.UserContext;
import com.mallcloud.product.dto.ProductUpsertRequest; import com.mallcloud.product.service.ProductService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/products")
public class ProductController {
    private final ProductService service; public ProductController(ProductService service) { this.service = service; }
    @GetMapping public ApiResponse<PageResponse<ProductSnapshot>> page(@RequestParam(defaultValue="1") long page, @RequestParam(defaultValue="20") long size, @RequestParam(required=false) Long categoryId) { return ApiResponse.success(service.page(page, size, categoryId)); }
    @GetMapping("/{id}") public ApiResponse<ProductSnapshot> get(@PathVariable Long id) { return ApiResponse.success(service.get(id)); }
    @PostMapping("/admin") public ApiResponse<ProductSnapshot> create(@Valid @RequestBody ProductUpsertRequest r) { UserContext.requireAdmin(); return ApiResponse.success(service.create(r)); }
    @PutMapping("/admin/{id}") public ApiResponse<ProductSnapshot> update(@PathVariable Long id, @Valid @RequestBody ProductUpsertRequest r) { UserContext.requireAdmin(); return ApiResponse.success(service.update(id, r)); }
    @DeleteMapping("/admin/{id}") public ApiResponse<Void> delete(@PathVariable Long id) { UserContext.requireAdmin(); service.delete(id); return ApiResponse.success(); }
}
