package com.mallcloud.stock.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper; import com.mallcloud.stock.entity.StockEntity; import org.apache.ibatis.annotations.*;
@Mapper public interface StockMapper extends BaseMapper<StockEntity> {
 @Update("UPDATE stock SET available_stock=available_stock-#{quantity},locked_stock=locked_stock+#{quantity},version=version+1,update_time=NOW() WHERE product_id=#{productId} AND available_stock>=#{quantity}") int reserve(@Param("productId") Long productId,@Param("quantity") int quantity);
 @Update("UPDATE stock SET available_stock=available_stock+#{quantity},locked_stock=locked_stock-#{quantity},version=version+1,update_time=NOW() WHERE product_id=#{productId} AND locked_stock>=#{quantity}") int release(@Param("productId") Long productId,@Param("quantity") int quantity);
 @Update("UPDATE stock SET locked_stock=locked_stock-#{quantity},total_stock=total_stock-#{quantity},version=version+1,update_time=NOW() WHERE product_id=#{productId} AND locked_stock>=#{quantity}") int confirm(@Param("productId") Long productId,@Param("quantity") int quantity);
}
