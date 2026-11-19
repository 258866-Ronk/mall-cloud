package com.mallcloud.api.stock;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record StockReservationRequest(@NotBlank String orderNo, @NotEmpty List<@Valid StockItem> items) {}
