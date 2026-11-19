package com.mallcloud.search.repository;
import com.mallcloud.search.document.ProductDocument; import org.springframework.data.domain.*; import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument,Long> {
 Page<ProductDocument> findByStatusAndProductNameContainingOrStatusAndSubtitleContaining(Integer status1,String name,Integer status2,String subtitle,Pageable pageable);
}
