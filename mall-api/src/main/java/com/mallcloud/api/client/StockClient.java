package com.mallcloud.api.client;

import com.mallcloud.api.stock.StockReservationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mall-stock-service", path = "/internal/stocks")
public interface StockClient {
    @PostMapping("/reserve") void reserve(@RequestBody StockReservationRequest request);
    @PostMapping("/release") void release(@RequestBody StockReservationRequest request);
    @PostMapping("/confirm") void confirm(@RequestBody StockReservationRequest request);
}
