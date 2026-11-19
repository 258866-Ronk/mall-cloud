package com.mallcloud.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.order.entity.OrderEntity;
import com.mallcloud.order.mapper.OrderMapper;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class OrderTimeoutJob {
    private static final Logger log = LoggerFactory.getLogger(OrderTimeoutJob.class);
    private final OrderMapper mapper; private final OrderService service;
    public OrderTimeoutJob(OrderMapper mapper, OrderService service) { this.mapper = mapper; this.service = service; }
    @Scheduled(fixedDelay = 60_000)
    public void cancelExpiredOrders() {
        var expired = mapper.selectPage(Page.of(1, 100), new LambdaQueryWrapper<OrderEntity>().eq(OrderEntity::getStatus, "PENDING_PAYMENT").lt(OrderEntity::getCreateTime, LocalDateTime.now().minusMinutes(30)).orderByAsc(OrderEntity::getCreateTime)).getRecords();
        for (OrderEntity order : expired) try { service.cancel(order.getOrderNo(), order.getUserId()); } catch (Exception e) { log.error("Failed to cancel expired order {}", order.getOrderNo(), e); }
    }
}
