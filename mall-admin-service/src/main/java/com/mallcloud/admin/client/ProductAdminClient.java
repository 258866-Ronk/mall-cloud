package com.mallcloud.admin.client;
import com.mallcloud.admin.dto.AdminProductRequest; import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.common.api.ApiResponse; import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="mall-product-service",contextId="productAdmin") public interface ProductAdminClient {
 @PostMapping("/api/products/admin")ApiResponse<ProductSnapshot> create(@RequestBody AdminProductRequest r);
 @PutMapping("/api/products/admin/{id}")ApiResponse<ProductSnapshot> update(@PathVariable("id")Long id,@RequestBody AdminProductRequest r);
 @DeleteMapping("/api/products/admin/{id}")ApiResponse<Void> delete(@PathVariable("id")Long id);
}
