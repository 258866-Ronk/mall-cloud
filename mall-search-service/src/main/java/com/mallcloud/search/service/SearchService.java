package com.mallcloud.search.service;
import com.mallcloud.common.api.PageResponse; import com.mallcloud.search.document.ProductDocument; import com.mallcloud.search.repository.ProductSearchRepository; import org.springframework.data.domain.*; import org.springframework.stereotype.Service;
@Service public class SearchService {
 private final ProductSearchRepository repository;public SearchService(ProductSearchRepository r){repository=r;}
 public PageResponse<ProductDocument> search(String keyword,int page,int size,String sort){Sort ordering="priceAsc".equals(sort)?Sort.by("price").ascending():"priceDesc".equals(sort)?Sort.by("price").descending():Sort.unsorted();Pageable pageable=PageRequest.of(Math.max(0,page-1),Math.min(size,100),ordering);Page<ProductDocument> result=(keyword==null||keyword.isBlank())?repository.findAll(pageable):repository.findByStatusAndProductNameContainingOrStatusAndSubtitleContaining(1,keyword,1,keyword,pageable);return PageResponse.of(result.getContent(),page,size,result.getTotalElements());}
}
