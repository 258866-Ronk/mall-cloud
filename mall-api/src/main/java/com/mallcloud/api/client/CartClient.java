package com.mallcloud.api.client;

import com.mallcloud.api.cart.CartLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "mall-cart-service", path = "/internal/carts")
public interface CartClient {
    @GetMapping("/{userId}/checked") List<CartLine> checkedItems(@PathVariable("userId") Long userId);
    @DeleteMapping("/{userId}/items") void removeItems(@PathVariable("userId") Long userId, @RequestParam("productIds") List<Long> productIds);
}
