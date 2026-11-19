package com.mallcloud.search.service;

import com.mallcloud.api.client.ProductClient; import com.mallcloud.api.product.ProductSnapshot; import com.mallcloud.search.document.ProductDocument; import com.mallcloud.search.repository.ProductSearchRepository;
import org.slf4j.Logger; import org.slf4j.LoggerFactory; import org.springframework.scheduling.annotation.Scheduled; import org.springframework.stereotype.Component;

@Component
public class ProductIndexSynchronizer {
    private static final Logger log = LoggerFactory.getLogger(ProductIndexSynchronizer.class);
    private final ProductClient products; private final ProductSearchRepository repository;
    public ProductIndexSynchronizer(ProductClient products, ProductSearchRepository repository) { this.products = products; this.repository = repository; }
    @Scheduled(initialDelay = 10_000, fixedDelay = 300_000)
    public void synchronize() {
        try {
            var documents = products.findAll().stream().map(this::document).toList();
            repository.saveAll(documents); log.info("Synchronized {} products to Elasticsearch", documents.size());
        } catch (Exception exception) { log.warn("Product index synchronization failed; it will retry", exception); }
    }
    private ProductDocument document(ProductSnapshot p) { return new ProductDocument(p.id(), p.categoryId(), p.productName(), p.subtitle(), p.mainImage(), p.price(), p.status()); }
}
