package com.mallcloud.order.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.math.BigDecimal;
@Data @TableName("order_item") public class OrderItemEntity { @TableId(type=IdType.ASSIGN_ID) private Long id; private Long orderId; private String orderNo; private Long productId; private String productName; private String productImage; private BigDecimal productPrice; private Integer quantity; private BigDecimal totalAmount; }
