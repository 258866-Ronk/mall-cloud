package com.mallcloud.cart.controller;
import com.mallcloud.api.cart.CartLine; import com.mallcloud.cart.service.CartService; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/internal/carts") public class InternalCartController {
 private final CartService service; public InternalCartController(CartService service){this.service=service;}
 @GetMapping("/{userId}/checked") public List<CartLine> checked(@PathVariable Long userId){return service.checked(userId);}
 @DeleteMapping("/{userId}/items") public void remove(@PathVariable Long userId,@RequestParam List<Long> productIds){service.remove(userId,productIds);}
}
