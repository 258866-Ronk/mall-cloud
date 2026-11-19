package com.mallcloud.seckill.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.math.BigDecimal; import java.time.LocalDateTime;
@Data @TableName("seckill_activity") public class SeckillActivityEntity {@TableId(type=IdType.ASSIGN_ID)private Long id;private Long productId;private String activityName;private BigDecimal seckillPrice;private Integer totalStock;private Integer limitPerUser;private LocalDateTime startTime;private LocalDateTime endTime;private Integer status;private LocalDateTime createTime;}
