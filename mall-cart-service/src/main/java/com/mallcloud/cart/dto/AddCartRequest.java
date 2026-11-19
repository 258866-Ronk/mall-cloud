package com.mallcloud.cart.dto;
import jakarta.validation.constraints.*;
public record AddCartRequest(@NotNull Long productId,@Min(1) @Max(99) int quantity) {}
