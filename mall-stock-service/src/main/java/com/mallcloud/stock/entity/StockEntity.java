package com.mallcloud.stock.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("stock") public class StockEntity { @TableId(type=IdType.ASSIGN_ID) private Long id; private Long productId; private Integer totalStock; private Integer availableStock; private Integer lockedStock; private Integer version; private LocalDateTime updateTime; }
