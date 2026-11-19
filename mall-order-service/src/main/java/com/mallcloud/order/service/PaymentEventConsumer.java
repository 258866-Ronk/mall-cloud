package com.mallcloud.order.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.mallcloud.api.client.StockClient; import com.mallcloud.api.message.*; import com.mallcloud.common.error.*; import com.mallcloud.order.entity.*; import com.mallcloud.order.mapper.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener; import org.springframework.stereotype.Component; import org.springframework.transaction.annotation.Transactional; import java.time.LocalDateTime;
@Component public class PaymentEventConsumer {
 private final OrderService service;private final OrderMapper orders;private final ProcessedMessageMapper messages;private final StockClient stock;
 public PaymentEventConsumer(OrderService s,OrderMapper o,ProcessedMessageMapper m,StockClient st){service=s;orders=o;messages=m;stock=st;}
 @RabbitListener(queues=MqNames.PAYMENT_SUCCEEDED_QUEUE) @Transactional public void consume(PaymentSucceededEvent e){
  if(messages.selectCount(new LambdaQueryWrapper<ProcessedMessageEntity>().eq(ProcessedMessageEntity::getMessageId,e.messageId()).eq(ProcessedMessageEntity::getConsumer,"order-payment"))>0)return;
  OrderEntity o=service.find(e.orderNo());if("PAID".equals(o.getStatus()))return;if(!"PENDING_PAYMENT".equals(o.getStatus())||o.getPayAmount().compareTo(e.amount())!=0||!o.getUserId().equals(e.userId()))throw new BizException(ErrorCode.ORDER_STATE_INVALID,"支付消息与订单不一致");
  stock.confirm(service.stockRequest(o));o.setStatus("PAID");o.setPayTime(e.paidAt());orders.updateById(o);ProcessedMessageEntity m=new ProcessedMessageEntity();m.setMessageId(e.messageId());m.setConsumer("order-payment");m.setProcessedTime(LocalDateTime.now());messages.insert(m);
 }
}
