package com.mallcloud.product.controller;

import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.product.service.ProductService; import org.springframework.web.bind.annotation.*; import java.util.List;

@RestController @RequestMapping("/internal/products")
public class InternalProductController {
    private final ProductService service; public InternalProductController(ProductService service) { this.service = service; }
    @GetMapping("/{id}") public ProductSnapshot get(@PathVariable Long id) { return service.get(id); }
    @GetMapping("/batch") public List<ProductSnapshot> batch(@RequestParam List<Long> ids) { return service.batch(ids); }
    @GetMapping("/all") public List<ProductSnapshot> all() { return service.all(); }
}
