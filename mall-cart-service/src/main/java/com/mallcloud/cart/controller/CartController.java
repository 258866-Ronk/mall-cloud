package com.mallcloud.cart.controller;
import com.mallcloud.api.cart.CartLine; import com.mallcloud.cart.dto.*; import com.mallcloud.cart.service.CartService; import com.mallcloud.common.api.ApiResponse; import com.mallcloud.common.security.UserContext; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/cart") public class CartController {
 private final CartService service; public CartController(CartService service){this.service=service;}
 @PostMapping("/items") public ApiResponse<CartLine> add(@Valid @RequestBody AddCartRequest r){return ApiResponse.success(service.add(UserContext.require().userId(),r));}
 @PutMapping("/items/{productId}") public ApiResponse<CartLine> update(@PathVariable Long productId,@Valid @RequestBody UpdateCartRequest r){return ApiResponse.success(service.update(UserContext.require().userId(),productId,r));}
 @DeleteMapping("/items/{productId}") public ApiResponse<Void> remove(@PathVariable Long productId){service.remove(UserContext.require().userId(),List.of(productId));return ApiResponse.success();}
 @GetMapping public ApiResponse<List<CartLine>> list(){return ApiResponse.success(service.list(UserContext.require().userId()));}
 @DeleteMapping public ApiResponse<Void> clear(){service.clear(UserContext.require().userId());return ApiResponse.success();}
}
