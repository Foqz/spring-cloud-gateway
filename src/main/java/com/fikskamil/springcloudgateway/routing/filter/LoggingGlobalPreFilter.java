package com.fikskamil.springcloudgateway.routing.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingGlobalPreFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //We could implement authorization and authentication here
        log.info("Hello from pre filter");
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    log.info("Hello from post filter");
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
