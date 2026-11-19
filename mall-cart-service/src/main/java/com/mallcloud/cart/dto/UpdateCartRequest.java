package com.mallcloud.cart.dto;
import jakarta.validation.constraints.*;
public record UpdateCartRequest(@Min(1) @Max(99) int quantity, boolean checked) {}
