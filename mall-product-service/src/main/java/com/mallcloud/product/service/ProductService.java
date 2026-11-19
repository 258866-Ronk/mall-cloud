package com.mallcloud.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper; import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallcloud.api.message.*; import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.common.api.PageResponse;
import com.mallcloud.common.error.*; import com.mallcloud.product.dto.ProductUpsertRequest; import com.mallcloud.product.entity.ProductEntity; import com.mallcloud.product.mapper.ProductMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime; import java.util.*;

@Service
public class ProductService {
    private final ProductMapper mapper; private final RabbitTemplate rabbitTemplate;
    public ProductService(ProductMapper mapper, RabbitTemplate rabbitTemplate) { this.mapper = mapper; this.rabbitTemplate = rabbitTemplate; }
    public PageResponse<ProductSnapshot> page(long page, long size, Long categoryId) {
        LambdaQueryWrapper<ProductEntity> query = new LambdaQueryWrapper<ProductEntity>().eq(ProductEntity::getStatus, 1).eq(categoryId != null, ProductEntity::getCategoryId, categoryId).orderByDesc(ProductEntity::getCreateTime);
        Page<ProductEntity> result = mapper.selectPage(Page.of(page, size), query);
        return PageResponse.of(result.getRecords().stream().map(this::snapshot).toList(), page, size, result.getTotal());
    }
    public ProductSnapshot get(Long id) { ProductEntity entity = mapper.selectById(id); if (entity == null) throw new BizException(ErrorCode.NOT_FOUND, "商品不存在"); return snapshot(entity); }
    public List<ProductSnapshot> batch(List<Long> ids) { if (ids == null || ids.isEmpty()) return List.of(); return mapper.selectBatchIds(ids).stream().map(this::snapshot).toList(); }
    public List<ProductSnapshot> all() { return mapper.selectList(new LambdaQueryWrapper<ProductEntity>().eq(ProductEntity::getStatus, 1)).stream().map(this::snapshot).toList(); }
    @Transactional public ProductSnapshot create(ProductUpsertRequest request) {
        ProductEntity entity = fill(new ProductEntity(), request); entity.setCreateTime(LocalDateTime.now()); entity.setUpdateTime(LocalDateTime.now()); mapper.insert(entity); publish("UPSERT", entity); return snapshot(entity);
    }
    @Transactional public ProductSnapshot update(Long id, ProductUpsertRequest request) {
        ProductEntity entity = mapper.selectById(id); if (entity == null) throw new BizException(ErrorCode.NOT_FOUND); fill(entity, request); entity.setUpdateTime(LocalDateTime.now()); mapper.updateById(entity); publish("UPSERT", entity); return snapshot(entity);
    }
    @Transactional public void delete(Long id) { ProductEntity entity = mapper.selectById(id); if (entity == null) return; mapper.deleteById(id); publish("DELETE", entity); }
    private ProductEntity fill(ProductEntity e, ProductUpsertRequest r) { e.setCategoryId(r.categoryId()); e.setProductName(r.productName()); e.setSubtitle(r.subtitle()); e.setDescription(r.description()); e.setPrice(r.price()); e.setMainImage(r.mainImage()); e.setStatus(r.status() == null ? 1 : r.status()); return e; }
    private ProductSnapshot snapshot(ProductEntity e) { return new ProductSnapshot(e.getId(), e.getCategoryId(), e.getProductName(), e.getSubtitle(), e.getMainImage(), e.getPrice(), e.getStatus()); }
    private void publish(String operation, ProductEntity entity) { rabbitTemplate.convertAndSend(MqNames.BUSINESS_EXCHANGE, MqNames.PRODUCT_CHANGED_KEY, new ProductChangedEvent(UUID.randomUUID().toString(), operation, snapshot(entity))); }
}
