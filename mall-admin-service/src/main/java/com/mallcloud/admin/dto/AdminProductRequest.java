package com.mallcloud.admin.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record AdminProductRequest(@NotNull Long categoryId,@NotBlank String productName,String subtitle,String description,@NotNull @DecimalMin("0.01")BigDecimal price,String mainImage,@Min(0)@Max(1)Integer status){}
