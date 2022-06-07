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
public class LoggingGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //could implement enchanting of request here
        log.info("Hello from pre request");
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    log.info("Hello from post request");
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
