package com.mallcloud.payment.dto;
import java.math.BigDecimal; import java.time.LocalDateTime;
public record PaymentView(String paymentNo,String orderNo,Long userId,BigDecimal amount,String payType,String status,String transactionNo,LocalDateTime createTime,LocalDateTime payTime){}
