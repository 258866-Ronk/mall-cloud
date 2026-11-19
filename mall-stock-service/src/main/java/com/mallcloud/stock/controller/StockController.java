package com.mallcloud.stock.controller;
import com.mallcloud.api.stock.StockReservationRequest; import com.mallcloud.common.api.ApiResponse; import com.mallcloud.common.security.UserContext; import com.mallcloud.stock.entity.StockEntity; import com.mallcloud.stock.service.StockService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController public class StockController {
 private final StockService service; public StockController(StockService service){this.service=service;}
 @GetMapping("/api/admin/stocks/{productId}") public ApiResponse<StockEntity> get(@PathVariable Long productId){UserContext.requireAdmin();return ApiResponse.success(service.get(productId));}
 @PutMapping("/api/admin/stocks/{productId}") public ApiResponse<StockEntity> set(@PathVariable Long productId,@RequestParam int quantity){UserContext.requireAdmin();return ApiResponse.success(service.set(productId,quantity));}
 @PostMapping("/internal/stocks/reserve") public void reserve(@Valid @RequestBody StockReservationRequest r){service.reserve(r);}
 @PostMapping("/internal/stocks/release") public void release(@Valid @RequestBody StockReservationRequest r){service.release(r);}
 @PostMapping("/internal/stocks/confirm") public void confirm(@Valid @RequestBody StockReservationRequest r){service.confirm(r);}
}
