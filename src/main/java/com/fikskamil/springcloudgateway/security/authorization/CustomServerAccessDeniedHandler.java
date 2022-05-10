package com.fikskamil.springcloudgateway.security.authorization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class CustomServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException accessDeniedException) {
        ServerHttpRequest request = exchange.getRequest();

        return exchange.getPrincipal()
                .doOnNext(principal -> log.info("user: [{}] does not have permission to access: [{}]",
                        principal.getName(),
                        request.getURI()))
                .flatMap(principal -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    String body = accessDeniedException.getMessage();
                    DataBuffer buffer = response
                            .bufferFactory()
                            .wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response
                            .writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }
}
