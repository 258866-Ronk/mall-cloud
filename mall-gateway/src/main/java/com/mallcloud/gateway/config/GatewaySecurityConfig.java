package com.mallcloud.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mallcloud.common.api.ApiResponse;
import com.mallcloud.common.security.AuthenticatedUser;
import com.mallcloud.common.security.JwtService;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class GatewaySecurityConfig {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public GatewaySecurityConfig(JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        ReactiveAuthenticationManager authenticationManager = authentication -> Mono.just(authentication);
        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(authenticationManager);
        jwtFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        jwtFilter.setSecurityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        jwtFilter.setServerAuthenticationConverter(exchange -> {
            String value = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (value == null || !value.startsWith("Bearer ")) return Mono.empty();
            try {
                AuthenticatedUser user = jwtService.parseToken(value.substring(7));
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.role().toUpperCase()));
                return Mono.just(new UsernamePasswordAuthenticationToken(user, value, authorities));
            } catch (Exception exception) {
                return Mono.error(new BadCredentialsException("Token 无效或已过期", exception));
            }
        });
        jwtFilter.setAuthenticationFailureHandler((webFilterExchange, exception) ->
                write(webFilterExchange.getExchange(), HttpStatus.UNAUTHORIZED, 401, "Token 无效或已过期"));

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/api/auth/login", "/api/users/register").permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/products/**", "/api/categories/**", "/api/search/products/**").permitAll()
                        .pathMatchers("/actuator/health", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .pathMatchers("/internal/**").denyAll()
                        .pathMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyExchange().authenticated())
                .exceptionHandling(errors -> errors
                        .authenticationEntryPoint((exchange, exception) -> write(exchange, HttpStatus.UNAUTHORIZED, 401, "请先登录"))
                        .accessDeniedHandler((exchange, exception) -> write(exchange, HttpStatus.FORBIDDEN, 403, "无权访问")))
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private Mono<Void> write(ServerWebExchange exchange, HttpStatus status, int code, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            byte[] bytes = objectMapper.writeValueAsString(ApiResponse.failure(code, message)).getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException exception) {
            return exchange.getResponse().setComplete();
        }
    }
}
