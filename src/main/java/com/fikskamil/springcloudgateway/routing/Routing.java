package com.fikskamil.springcloudgateway.routing;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routing {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.
                                addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org"))
                .build();
    }
}
