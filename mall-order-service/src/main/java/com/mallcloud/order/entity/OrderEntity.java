package com.mallcloud.order.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.math.BigDecimal; import java.time.LocalDateTime;
@Data @TableName("mall_order") public class OrderEntity {
 @TableId private Long id; private String orderNo; private String requestNo; private Long userId; private BigDecimal totalAmount; private BigDecimal payAmount; private String status;
 private String receiverName; private String receiverPhone; private String receiverAddress; private LocalDateTime createTime; private LocalDateTime payTime; private LocalDateTime shipTime; private LocalDateTime finishTime; private LocalDateTime cancelTime;
}
