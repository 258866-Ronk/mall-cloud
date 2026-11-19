package com.mallcloud.api.cart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartLine(Long productId, String productName, String productImage, BigDecimal price, int quantity, boolean checked, LocalDateTime createTime) {}
