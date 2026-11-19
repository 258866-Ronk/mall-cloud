package com.mallcloud.api.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockItem(@NotNull Long productId, @Min(1) int quantity) {}
