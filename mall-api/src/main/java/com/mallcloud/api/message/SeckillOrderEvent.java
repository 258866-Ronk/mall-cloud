package com.mallcloud.api.message;

import java.math.BigDecimal;

public record SeckillOrderEvent(String messageId, Long activityId, Long userId, Long productId, int quantity, BigDecimal seckillPrice, String requestNo,
                                String receiverName, String receiverPhone, String receiverAddress) {}
