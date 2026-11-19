package com.mallcloud.stock.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper; import com.mallcloud.stock.entity.StockReservationEntity; import org.apache.ibatis.annotations.*;
@Mapper public interface StockReservationMapper extends BaseMapper<StockReservationEntity> {
 @Update("UPDATE stock_reservation SET status=#{target},update_time=NOW() WHERE order_no=#{orderNo} AND product_id=#{productId} AND status=#{source}") int changeStatus(@Param("orderNo") String orderNo,@Param("productId") Long productId,@Param("source") String source,@Param("target") String target);
}
