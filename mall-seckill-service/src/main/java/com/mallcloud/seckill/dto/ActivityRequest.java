package com.mallcloud.seckill.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.time.LocalDateTime;
public record ActivityRequest(@NotNull Long productId,@NotBlank String activityName,@NotNull @DecimalMin("0.01") BigDecimal seckillPrice,@Min(1)int totalStock,@Min(1)int limitPerUser,@NotNull LocalDateTime startTime,@NotNull LocalDateTime endTime){}
