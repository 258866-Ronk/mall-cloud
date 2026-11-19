package com.mallcloud.admin.client;
import com.mallcloud.api.order.OrderSummary; import com.mallcloud.common.api.*; import org.springframework.cloud.openfeign.FeignClient; import org.springframework.web.bind.annotation.*;
@FeignClient(name="mall-order-service",contextId="orderAdmin") public interface OrderAdminClient {
 @GetMapping("/api/orders")ApiResponse<PageResponse<OrderSummary>> page(@RequestParam("page")long page,@RequestParam("size")long size);
 @PostMapping("/api/admin/orders/{orderNo}/ship")ApiResponse<Void> ship(@PathVariable("orderNo")String orderNo);
}
