package com.mallcloud.product.dto;

import jakarta.validation.constraints.*; import java.math.BigDecimal;

public record ProductUpsertRequest(@NotNull Long categoryId, @NotBlank @Size(max=120) String productName, @Size(max=255) String subtitle,
                                   @Size(max=5000) String description, @NotNull @DecimalMin("0.01") BigDecimal price, String mainImage,
                                   @Min(0) @Max(1) Integer status) {}
