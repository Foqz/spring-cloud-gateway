package com.fikskamil.springcloudgateway.routing;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HttpBinConfigProps.class)
public class Routing {

    private final HttpBinConfigProps httpBinConfigProps;

    public Routing(HttpBinConfigProps httpBinConfigProps) {
        this.httpBinConfigProps = httpBinConfigProps;
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = httpBinConfigProps.getHttpbin();
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.
                                addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .build();
    }
}
