package com.mallcloud.order.entity;
import com.baomidou.mybatisplus.annotation.*; import lombok.Data; import java.time.LocalDateTime;
@Data @TableName("processed_message") public class ProcessedMessageEntity { @TableId(type=IdType.ASSIGN_ID) private Long id; private String messageId; private String consumer; private LocalDateTime processedTime; }
