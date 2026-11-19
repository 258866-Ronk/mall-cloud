package com.mallcloud.gateway.filter;

import com.mallcloud.common.security.AuthenticatedUser;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class IdentityHeaderGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerWebExchange cleared = exchange.mutate().request(builder -> builder.headers(headers -> {
            headers.remove("X-User-Id");
            headers.remove("X-Username");
            headers.remove("X-User-Role");
        })).build();

        return cleared.getPrincipal()
                .filter(Authentication.class::isInstance)
                .cast(Authentication.class)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(AuthenticatedUser.class::isInstance)
                .cast(AuthenticatedUser.class)
                .map(user -> cleared.mutate().request(builder -> builder.headers(headers -> {
                    headers.set("X-User-Id", user.userId().toString());
                    headers.set("X-Username", user.username());
                    headers.set("X-User-Role", user.role());
                })).build())
                .defaultIfEmpty(cleared)
                .flatMap(chain::filter);
    }

    @Override public int getOrder() { return -100; }
}
