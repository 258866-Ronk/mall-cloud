package com.mallcloud.payment.controller;
import com.mallcloud.common.api.ApiResponse; import com.mallcloud.common.security.*; import com.mallcloud.payment.dto.PaymentView; import com.mallcloud.payment.service.PaymentService; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/payments") public class PaymentController {
 private final PaymentService service;public PaymentController(PaymentService s){service=s;}
 @PostMapping("/orders/{orderNo}") public ApiResponse<PaymentView> pay(@PathVariable String orderNo){return ApiResponse.success(service.pay(orderNo,UserContext.require().userId()));}
 @GetMapping("/{paymentNo}") public ApiResponse<PaymentView> get(@PathVariable String paymentNo){AuthenticatedUser u=UserContext.require();return ApiResponse.success(service.get(paymentNo,u.userId(),u.isAdmin()));}
}
