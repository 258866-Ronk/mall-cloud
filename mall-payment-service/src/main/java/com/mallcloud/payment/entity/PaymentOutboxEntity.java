package com.mallcloud.payment.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("payment_outbox") public class PaymentOutboxEntity {@TableId(type=IdType.ASSIGN_ID)private Long id;private String messageId;private String paymentNo;private String status;private Integer retryCount;private LocalDateTime nextRetryTime;private LocalDateTime createTime;private LocalDateTime sentTime;}
