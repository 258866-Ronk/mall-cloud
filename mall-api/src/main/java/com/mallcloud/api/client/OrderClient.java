package com.mallcloud.api.client;

import com.mallcloud.api.order.DirectOrderRequest;
import com.mallcloud.api.order.OrderSummary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mall-order-service", path = "/internal/orders")
public interface OrderClient {
    @PostMapping("/direct") OrderSummary createDirect(@RequestBody DirectOrderRequest request);
    @GetMapping("/{orderNo}") OrderSummary find(@PathVariable("orderNo") String orderNo);
}
