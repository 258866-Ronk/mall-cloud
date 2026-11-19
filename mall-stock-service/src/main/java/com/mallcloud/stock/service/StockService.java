package com.mallcloud.stock.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.mallcloud.api.stock.*; import com.mallcloud.common.error.*;
import com.mallcloud.stock.entity.*; import com.mallcloud.stock.mapper.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service public class StockService {
 private final StockMapper stockMapper; private final StockReservationMapper reservationMapper;
 public StockService(StockMapper stockMapper,StockReservationMapper reservationMapper){this.stockMapper=stockMapper;this.reservationMapper=reservationMapper;}
 @Transactional public void reserve(StockReservationRequest request){
  for(StockItem item:request.items()){
   Long exists=reservationMapper.selectCount(new LambdaQueryWrapper<StockReservationEntity>().eq(StockReservationEntity::getOrderNo,request.orderNo()).eq(StockReservationEntity::getProductId,item.productId()));
   if(exists>0) continue;
   if(stockMapper.reserve(item.productId(),item.quantity())!=1) throw new BizException(ErrorCode.STOCK_NOT_ENOUGH,"商品 "+item.productId()+" 库存不足");
   StockReservationEntity r=new StockReservationEntity();r.setOrderNo(request.orderNo());r.setProductId(item.productId());r.setQuantity(item.quantity());r.setStatus("LOCKED");r.setCreateTime(LocalDateTime.now());r.setUpdateTime(LocalDateTime.now());reservationMapper.insert(r);
  }
 }
 @Transactional public void release(StockReservationRequest request){ for(StockItem item:request.items()) if(reservationMapper.changeStatus(request.orderNo(),item.productId(),"LOCKED","RELEASED")==1 && stockMapper.release(item.productId(),item.quantity())!=1) throw new IllegalStateException("locked stock inconsistent"); }
 @Transactional public void confirm(StockReservationRequest request){ for(StockItem item:request.items()) if(reservationMapper.changeStatus(request.orderNo(),item.productId(),"LOCKED","CONFIRMED")==1 && stockMapper.confirm(item.productId(),item.quantity())!=1) throw new IllegalStateException("locked stock inconsistent"); }
 public StockEntity get(Long productId){StockEntity s=stockMapper.selectOne(new LambdaQueryWrapper<StockEntity>().eq(StockEntity::getProductId,productId));if(s==null)throw new BizException(ErrorCode.NOT_FOUND,"库存记录不存在");return s;}
 @Transactional public StockEntity set(Long productId,int quantity){StockEntity s=stockMapper.selectOne(new LambdaQueryWrapper<StockEntity>().eq(StockEntity::getProductId,productId));if(s==null){s=new StockEntity();s.setProductId(productId);s.setLockedStock(0);s.setVersion(0);}s.setTotalStock(quantity+s.getLockedStock());s.setAvailableStock(quantity);s.setUpdateTime(LocalDateTime.now());if(s.getId()==null)stockMapper.insert(s);else stockMapper.updateById(s);return s;}
}
