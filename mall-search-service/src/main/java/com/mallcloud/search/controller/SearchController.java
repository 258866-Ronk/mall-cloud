package com.mallcloud.search.controller;
import com.mallcloud.common.api.*; import com.mallcloud.search.document.ProductDocument; import com.mallcloud.search.service.SearchService; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/search/products") public class SearchController {
 private final SearchService service;public SearchController(SearchService s){service=s;}
 @GetMapping public ApiResponse<PageResponse<ProductDocument>> search(@RequestParam(required=false)String keyword,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="20")int size,@RequestParam(defaultValue="default")String sort){return ApiResponse.success(service.search(keyword,page,size,sort));}
}
