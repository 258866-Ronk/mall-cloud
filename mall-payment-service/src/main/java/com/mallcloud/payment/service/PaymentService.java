package com.mallcloud.payment.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.mallcloud.api.client.OrderClient; import com.mallcloud.api.order.OrderSummary; import com.mallcloud.common.error.*; import com.mallcloud.common.id.SnowflakeIdGenerator; import com.mallcloud.payment.dto.PaymentView; import com.mallcloud.payment.entity.*; import com.mallcloud.payment.mapper.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.time.LocalDateTime; import java.util.UUID;
@Service public class PaymentService {
 private final PaymentMapper payments;private final PaymentOutboxMapper outbox;private final OrderClient orders;private final SnowflakeIdGenerator ids;
 public PaymentService(PaymentMapper p,PaymentOutboxMapper o,OrderClient orders,SnowflakeIdGenerator ids){payments=p;outbox=o;this.orders=orders;this.ids=ids;}
 @Transactional public PaymentView pay(String orderNo,Long userId){
  PaymentEntity old=payments.selectOne(new LambdaQueryWrapper<PaymentEntity>().eq(PaymentEntity::getOrderNo,orderNo));if(old!=null){if(!old.getUserId().equals(userId))throw new BizException(ErrorCode.FORBIDDEN);return view(old);}
  OrderSummary order=orders.find(orderNo);if(!order.userId().equals(userId))throw new BizException(ErrorCode.FORBIDDEN);if(!"PENDING_PAYMENT".equals(order.status()))throw new BizException(ErrorCode.ORDER_STATE_INVALID,"订单不是待付款状态");
  long id=ids.nextId();LocalDateTime now=LocalDateTime.now();PaymentEntity p=new PaymentEntity();p.setId(id);p.setPaymentNo(String.valueOf(id));p.setOrderNo(orderNo);p.setUserId(userId);p.setAmount(order.payAmount());p.setPayType("MOCK");p.setStatus("SUCCEEDED");p.setTransactionNo("MOCK-"+UUID.randomUUID());p.setCreateTime(now);p.setPayTime(now);payments.insert(p);
  PaymentOutboxEntity event=new PaymentOutboxEntity();event.setMessageId(UUID.randomUUID().toString());event.setPaymentNo(p.getPaymentNo());event.setStatus("NEW");event.setRetryCount(0);event.setNextRetryTime(now);event.setCreateTime(now);outbox.insert(event);return view(p);
 }
 public PaymentView get(String paymentNo,Long userId,boolean admin){PaymentEntity p=payments.selectOne(new LambdaQueryWrapper<PaymentEntity>().eq(PaymentEntity::getPaymentNo,paymentNo));if(p==null)throw new BizException(ErrorCode.NOT_FOUND);if(!admin&&!p.getUserId().equals(userId))throw new BizException(ErrorCode.FORBIDDEN);return view(p);}
 private PaymentView view(PaymentEntity p){return new PaymentView(p.getPaymentNo(),p.getOrderNo(),p.getUserId(),p.getAmount(),p.getPayType(),p.getStatus(),p.getTransactionNo(),p.getCreateTime(),p.getPayTime());}
}
