package com.mallcloud.order.controller;
import com.mallcloud.api.order.*; import com.mallcloud.common.api.*; import com.mallcloud.common.security.*; import com.mallcloud.order.dto.*; import com.mallcloud.order.service.OrderService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
@RestController public class OrderController {
 private final OrderService service;public OrderController(OrderService s){service=s;}
 @PostMapping("/api/orders") public ApiResponse<OrderSummary> create(@Valid @RequestBody CreateOrderRequest r){return ApiResponse.success(service.createFromCart(UserContext.require().userId(),r));}
 @GetMapping("/api/orders/{orderNo}") public ApiResponse<OrderDetail> detail(@PathVariable String orderNo){AuthenticatedUser u=UserContext.require();return ApiResponse.success(service.detail(orderNo,u.userId(),u.isAdmin()));}
 @GetMapping("/api/orders") public ApiResponse<PageResponse<OrderSummary>> page(@RequestParam(defaultValue="1")long page,@RequestParam(defaultValue="20")long size){AuthenticatedUser u=UserContext.require();return ApiResponse.success(service.page(u.userId(),u.isAdmin(),page,size));}
 @PostMapping("/api/orders/{orderNo}/cancel") public ApiResponse<Void> cancel(@PathVariable String orderNo){service.cancel(orderNo,UserContext.require().userId());return ApiResponse.success();}
 @PostMapping("/api/orders/{orderNo}/complete") public ApiResponse<Void> complete(@PathVariable String orderNo){service.complete(orderNo,UserContext.require().userId());return ApiResponse.success();}
 @PostMapping("/api/admin/orders/{orderNo}/ship") public ApiResponse<Void> ship(@PathVariable String orderNo){UserContext.requireAdmin();service.ship(orderNo);return ApiResponse.success();}
 @PostMapping("/internal/orders/direct") public OrderSummary direct(@Valid @RequestBody DirectOrderRequest r){return service.createDirect(r);}
 @GetMapping("/internal/orders/{orderNo}") public OrderSummary internalFind(@PathVariable String orderNo){return service.findSummary(orderNo);}
}
