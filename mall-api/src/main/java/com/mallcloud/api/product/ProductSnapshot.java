package com.mallcloud.api.product;

import java.math.BigDecimal;

public record ProductSnapshot(Long id, Long categoryId, String productName, String subtitle, String mainImage, BigDecimal price, Integer status) {
    public boolean available() { return Integer.valueOf(1).equals(status); }
}
