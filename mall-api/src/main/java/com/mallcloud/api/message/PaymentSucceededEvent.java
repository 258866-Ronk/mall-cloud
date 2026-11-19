package com.mallcloud.api.message;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentSucceededEvent(String messageId, String paymentNo, String orderNo, Long userId, BigDecimal amount, LocalDateTime paidAt) {}
