package com.mallcloud.product.entity;

import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.math.BigDecimal; import java.time.LocalDateTime;

@Data @TableName("product")
public class ProductEntity {
    @TableId(type=IdType.ASSIGN_ID) private Long id; private Long categoryId; private String productName; private String subtitle; private String description;
    private BigDecimal price; private String mainImage; private Integer status; private LocalDateTime createTime; private LocalDateTime updateTime;
}
