package com.mallcloud.cart.service;

import com.fasterxml.jackson.core.JsonProcessingException; import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallcloud.api.cart.CartLine; import com.mallcloud.api.client.ProductClient; import com.mallcloud.api.product.ProductSnapshot;
import com.mallcloud.cart.dto.*; import com.mallcloud.common.error.*; import org.springframework.data.redis.core.StringRedisTemplate; import org.springframework.stereotype.Service;
import java.time.*; import java.util.*; import java.util.function.Function; import java.util.stream.Collectors;

@Service public class CartService {
 private static final Duration TTL=Duration.ofDays(30); private final StringRedisTemplate redis; private final ObjectMapper mapper; private final ProductClient productClient;
 public CartService(StringRedisTemplate redis,ObjectMapper mapper,ProductClient productClient){this.redis=redis;this.mapper=mapper;this.productClient=productClient;}
 public CartLine add(Long userId,AddCartRequest request){
  ProductSnapshot p=productClient.findById(request.productId()); if(p==null||!p.available())throw new BizException(ErrorCode.PRODUCT_UNAVAILABLE);
  CartLine old=read(userId,request.productId()); int quantity=(old==null?0:old.quantity())+request.quantity(); if(quantity>99)throw new BizException(ErrorCode.BAD_REQUEST,"单件商品最多购买 99 件");
  CartLine line=new CartLine(p.id(),p.productName(),p.mainImage(),p.price(),quantity,true,old==null?LocalDateTime.now():old.createTime()); write(userId,line); return line;
 }
 public CartLine update(Long userId,Long productId,UpdateCartRequest r){CartLine old=read(userId,productId);if(old==null)throw new BizException(ErrorCode.NOT_FOUND,"购物车商品不存在");CartLine line=new CartLine(old.productId(),old.productName(),old.productImage(),old.price(),r.quantity(),r.checked(),old.createTime());write(userId,line);return line;}
 public void remove(Long userId,List<Long> productIds){if(productIds!=null&&!productIds.isEmpty())redis.opsForHash().delete(key(userId),productIds.stream().map(String::valueOf).toArray());}
 public List<CartLine> list(Long userId){
  List<CartLine> stored=redis.opsForHash().values(key(userId)).stream().map(v->parse((String)v)).filter(Objects::nonNull).toList(); if(stored.isEmpty())return List.of();
  Map<Long,ProductSnapshot> products=productClient.findByIds(stored.stream().map(CartLine::productId).toList()).stream().collect(Collectors.toMap(ProductSnapshot::id,Function.identity()));
  List<CartLine> refreshed=new ArrayList<>(); for(CartLine old:stored){ProductSnapshot p=products.get(old.productId());if(p==null||!p.available()){redis.opsForHash().delete(key(userId),String.valueOf(old.productId()));continue;}CartLine line=new CartLine(p.id(),p.productName(),p.mainImage(),p.price(),old.quantity(),old.checked(),old.createTime());write(userId,line);refreshed.add(line);} return refreshed;
 }
 public List<CartLine> checked(Long userId){return list(userId).stream().filter(CartLine::checked).toList();}
 public void clear(Long userId){redis.delete(key(userId));}
 private CartLine read(Long userId,Long productId){Object value=redis.opsForHash().get(key(userId),String.valueOf(productId));return value==null?null:parse((String)value);}
 private void write(Long userId,CartLine line){try{redis.opsForHash().put(key(userId),String.valueOf(line.productId()),mapper.writeValueAsString(line));redis.expire(key(userId),TTL);}catch(JsonProcessingException e){throw new IllegalStateException(e);}}
 private CartLine parse(String value){try{return mapper.readValue(value,CartLine.class);}catch(JsonProcessingException e){return null;}}
 private String key(Long userId){return "mall:cart:"+userId;}
}
