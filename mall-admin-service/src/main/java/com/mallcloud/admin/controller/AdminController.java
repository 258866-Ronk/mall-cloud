package com.mallcloud.admin.controller;
import com.mallcloud.admin.client.*; import com.mallcloud.admin.dto.AdminProductRequest; import com.mallcloud.api.order.OrderSummary; import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.api.user.UserSummary; import com.mallcloud.common.api.*; import com.mallcloud.common.security.UserContext; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api/admin") public class AdminController {
 private final UserAdminClient users;private final ProductAdminClient products;private final OrderAdminClient orders;private final StockAdminClient stocks;
 public AdminController(UserAdminClient u,ProductAdminClient p,OrderAdminClient o,StockAdminClient s){users=u;products=p;orders=o;stocks=s;}
 private void check(){UserContext.requireAdmin();}
 @GetMapping("/users")public ApiResponse<PageResponse<UserSummary>> users(@RequestParam(defaultValue="1")long page,@RequestParam(defaultValue="20")long size){check();return users.page(page,size);}
 @PutMapping("/users/{id}/status")public ApiResponse<UserSummary> userStatus(@PathVariable Long id,@RequestParam int status){check();return users.status(id,status);}
 @PostMapping("/products")public ApiResponse<ProductSnapshot> createProduct(@Valid @RequestBody AdminProductRequest r){check();return products.create(r);}
 @PutMapping("/products/{id}")public ApiResponse<ProductSnapshot> updateProduct(@PathVariable Long id,@Valid @RequestBody AdminProductRequest r){check();return products.update(id,r);}
 @DeleteMapping("/products/{id}")public ApiResponse<Void> deleteProduct(@PathVariable Long id){check();return products.delete(id);}
 @GetMapping("/orders")public ApiResponse<PageResponse<OrderSummary>> orders(@RequestParam(defaultValue="1")long page,@RequestParam(defaultValue="20")long size){check();return orders.page(page,size);}
 @PostMapping("/orders/{orderNo}/ship")public ApiResponse<Void> ship(@PathVariable String orderNo){check();return orders.ship(orderNo);}
 @GetMapping("/stocks/{productId}")public ApiResponse<Map<String,Object>> stock(@PathVariable Long productId){check();return stocks.get(productId);}
 @PutMapping("/stocks/{productId}")public ApiResponse<Map<String,Object>> stock(@PathVariable Long productId,@RequestParam int quantity){check();return stocks.set(productId,quantity);}
}
