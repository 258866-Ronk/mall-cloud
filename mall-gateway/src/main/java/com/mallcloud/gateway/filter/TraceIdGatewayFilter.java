package com.mallcloud.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Component
public class TraceIdGatewayFilter implements GlobalFilter, Ordered {
    @Override public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String incoming = exchange.getRequest().getHeaders().getFirst("X-Trace-Id");
        String traceId = incoming == null || incoming.isBlank() ? UUID.randomUUID().toString().replace("-", "") : incoming;
        exchange.getResponse().getHeaders().set("X-Trace-Id", traceId);
        return chain.filter(exchange.mutate().request(builder -> builder.header("X-Trace-Id", traceId)).build());
    }
    @Override public int getOrder() { return -200; }
}
