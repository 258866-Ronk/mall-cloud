package com.mallcloud.payment.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.baomidou.mybatisplus.extension.plugins.pagination.Page; import com.mallcloud.api.message.*; import com.mallcloud.payment.entity.*; import com.mallcloud.payment.mapper.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component; import java.time.LocalDateTime; import java.util.List;
@Component public class OutboxPublisher {
 private final PaymentOutboxMapper outbox;private final PaymentMapper payments;private final RabbitTemplate rabbit;
 public OutboxPublisher(PaymentOutboxMapper o,PaymentMapper p,RabbitTemplate r){outbox=o;payments=p;rabbit=r;}
 @Scheduled(fixedDelay=2000) public void publish(){
  List<PaymentOutboxEntity> events=outbox.selectPage(Page.of(1,50),new LambdaQueryWrapper<PaymentOutboxEntity>().eq(PaymentOutboxEntity::getStatus,"NEW").le(PaymentOutboxEntity::getNextRetryTime,LocalDateTime.now()).orderByAsc(PaymentOutboxEntity::getCreateTime)).getRecords();
  for(PaymentOutboxEntity e:events){try{PaymentEntity p=payments.selectOne(new LambdaQueryWrapper<PaymentEntity>().eq(PaymentEntity::getPaymentNo,e.getPaymentNo()));rabbit.convertAndSend(MqNames.BUSINESS_EXCHANGE,MqNames.PAYMENT_SUCCEEDED_KEY,new PaymentSucceededEvent(e.getMessageId(),p.getPaymentNo(),p.getOrderNo(),p.getUserId(),p.getAmount(),p.getPayTime()));e.setStatus("SENT");e.setSentTime(LocalDateTime.now());outbox.updateById(e);}catch(Exception ex){e.setRetryCount(e.getRetryCount()+1);e.setNextRetryTime(LocalDateTime.now().plusSeconds(Math.min(60,e.getRetryCount()*5L)));outbox.updateById(e);}}
 }
}
