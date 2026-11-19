package com.mallcloud.api.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DirectOrderRequest(@NotBlank String requestNo, @NotNull Long userId, @NotNull Long productId, @Min(1) int quantity,
                                 BigDecimal unitPrice, @NotBlank String receiverName, @NotBlank String receiverPhone, @NotBlank String receiverAddress) {}
