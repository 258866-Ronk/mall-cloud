package com.mallcloud.product.dto;

import jakarta.validation.constraints.*;

public record CategoryRequest(@NotNull Long parentId, @NotBlank @Size(max = 64) String categoryName,
                              @Min(1) int level, @Min(0) int sort, @Min(0) @Max(1) int status) {}
