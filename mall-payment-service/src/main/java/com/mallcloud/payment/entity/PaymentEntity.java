package com.mallcloud.payment.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.math.BigDecimal; import java.time.LocalDateTime;
@Data @TableName("payment") public class PaymentEntity {@TableId private Long id;private String paymentNo;private String orderNo;private Long userId;private BigDecimal amount;private String payType;private String status;private String transactionNo;private LocalDateTime createTime;private LocalDateTime payTime;}
