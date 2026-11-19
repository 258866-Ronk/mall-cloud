package com.mallcloud.api.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummary(String orderNo, Long userId, BigDecimal totalAmount, BigDecimal payAmount, String status,
                           LocalDateTime createTime, LocalDateTime payTime) {}
