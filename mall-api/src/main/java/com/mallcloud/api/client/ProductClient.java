package com.mallcloud.api.client;

import com.mallcloud.api.product.ProductSnapshot;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "mall-product-service", path = "/internal/products")
public interface ProductClient {
    @GetMapping("/{id}") ProductSnapshot findById(@PathVariable("id") Long id);
    @GetMapping("/batch") List<ProductSnapshot> findByIds(@RequestParam("ids") List<Long> ids);
    @GetMapping("/all") List<ProductSnapshot> findAll();
}
