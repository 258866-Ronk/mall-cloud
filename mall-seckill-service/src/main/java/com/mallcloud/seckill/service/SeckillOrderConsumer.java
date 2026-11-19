package com.mallcloud.seckill.service;
import com.mallcloud.api.client.OrderClient; import com.mallcloud.api.message.*; import com.mallcloud.api.order.*; import com.mallcloud.seckill.config.SeckillConfig; import org.springframework.amqp.rabbit.annotation.RabbitListener; import org.springframework.stereotype.Component;
@Component public class SeckillOrderConsumer {
 private final OrderClient orders;private final SeckillService service;public SeckillOrderConsumer(OrderClient o,SeckillService s){orders=o;service=s;}
 @RabbitListener(queues=MqNames.SECKILL_ORDER_QUEUE) public void create(SeckillOrderEvent e){OrderSummary order=orders.createDirect(new DirectOrderRequest(e.requestNo(),e.userId(),e.productId(),e.quantity(),e.seckillPrice(),e.receiverName(),e.receiverPhone(),e.receiverAddress()));service.success(e.requestNo(),order.orderNo());}
 @RabbitListener(queues=SeckillConfig.DEAD_QUEUE) public void dead(SeckillOrderEvent e){service.fail(e);}
}
