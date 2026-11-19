package com.mallcloud.search.service;
import com.mallcloud.api.message.*; import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.search.document.ProductDocument; import com.mallcloud.search.repository.ProductSearchRepository; import org.springframework.amqp.rabbit.annotation.RabbitListener; import org.springframework.stereotype.Component;
@Component public class ProductIndexConsumer {
 private final ProductSearchRepository repository;public ProductIndexConsumer(ProductSearchRepository r){repository=r;}
 @RabbitListener(queues=MqNames.PRODUCT_CHANGED_QUEUE) public void consume(ProductChangedEvent e){ProductSnapshot p=e.product();if("DELETE".equals(e.operation()))repository.deleteById(p.id());else repository.save(new ProductDocument(p.id(),p.categoryId(),p.productName(),p.subtitle(),p.mainImage(),p.price(),p.status()));}
}
