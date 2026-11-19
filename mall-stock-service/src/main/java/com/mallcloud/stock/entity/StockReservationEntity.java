package com.mallcloud.stock.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("stock_reservation") public class StockReservationEntity { @TableId(type=IdType.ASSIGN_ID) private Long id; private String orderNo; private Long productId; private Integer quantity; private String status; private LocalDateTime createTime; private LocalDateTime updateTime; }
