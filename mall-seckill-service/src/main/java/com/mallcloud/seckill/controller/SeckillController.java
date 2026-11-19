package com.mallcloud.seckill.controller;
import com.mallcloud.common.api.ApiResponse; import com.mallcloud.common.security.UserContext; import com.mallcloud.seckill.dto.*; import com.mallcloud.seckill.entity.SeckillActivityEntity; import com.mallcloud.seckill.service.SeckillService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController public class SeckillController {
 private final SeckillService service;public SeckillController(SeckillService s){service=s;}
 @GetMapping("/api/seckill/activities") public ApiResponse<List<SeckillActivityEntity>> list(){return ApiResponse.success(service.list());}
 @PostMapping("/api/seckill/{activityId}") public ApiResponse<SeckillResult> attempt(@PathVariable Long activityId,@Valid @RequestBody SeckillRequest r){return ApiResponse.success(service.attempt(activityId,UserContext.require().userId(),r));}
 @GetMapping("/api/seckill/results/{requestNo}") public ApiResponse<SeckillResult> result(@PathVariable String requestNo){return ApiResponse.success(service.result(requestNo,UserContext.require().userId()));}
 @PostMapping("/api/admin/seckill/activities") public ApiResponse<SeckillActivityEntity> create(@Valid @RequestBody ActivityRequest r){UserContext.requireAdmin();return ApiResponse.success(service.create(r));}
}
