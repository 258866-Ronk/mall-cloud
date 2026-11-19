package com.mallcloud.order.service;
import com.mallcloud.api.cart.CartLine; import com.mallcloud.order.entity.*; import com.mallcloud.order.mapper.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service public class OrderPersistenceService {
 private final OrderMapper orderMapper;private final OrderItemMapper itemMapper;public OrderPersistenceService(OrderMapper o,OrderItemMapper i){orderMapper=o;itemMapper=i;}
 @Transactional public void save(OrderEntity order,List<CartLine> lines){orderMapper.insert(order);for(CartLine l:lines){OrderItemEntity item=new OrderItemEntity();item.setOrderId(order.getId());item.setOrderNo(order.getOrderNo());item.setProductId(l.productId());item.setProductName(l.productName());item.setProductImage(l.productImage());item.setProductPrice(l.price());item.setQuantity(l.quantity());item.setTotalAmount(l.price().multiply(java.math.BigDecimal.valueOf(l.quantity())));itemMapper.insert(item);}}
}
