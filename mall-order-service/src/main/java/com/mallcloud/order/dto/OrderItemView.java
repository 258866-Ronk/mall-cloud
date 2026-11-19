package com.mallcloud.order.dto;
import java.math.BigDecimal;
public record OrderItemView(Long productId,String productName,String productImage,BigDecimal productPrice,int quantity,BigDecimal totalAmount){}
