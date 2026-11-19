package com.mallcloud.admin.client;
import com.mallcloud.common.api.ApiResponse; import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*; import java.util.Map;
@FeignClient(name="mall-stock-service",contextId="stockAdmin") public interface StockAdminClient {
 @GetMapping("/api/admin/stocks/{productId}")ApiResponse<Map<String,Object>> get(@PathVariable("productId")Long productId);
 @PutMapping("/api/admin/stocks/{productId}")ApiResponse<Map<String,Object>> set(@PathVariable("productId")Long productId,@RequestParam("quantity")int quantity);
}
