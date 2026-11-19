package com.mallcloud.order.dto;
import com.mallcloud.api.order.OrderSummary; import java.util.List;
public record OrderDetail(OrderSummary order,List<OrderItemView> items,String receiverName,String receiverPhone,String receiverAddress){}
