package com.fikskamil.springcloudgateway.routing.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UriProperties.class)
public class RoutingConfiguration {

    private final UriProperties uriProperties;

    public RoutingConfiguration(UriProperties uriProperties) {
        this.uriProperties = uriProperties;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        String targetServiceUri = uriProperties.getTargetServiceUri();
        return builder.routes()
                //httpBin example from enhance headers
                .route(predicateSpec -> predicateSpec
                        .path("/get")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.
                                addRequestHeader("Hello", "World"))
                        .uri(uriProperties.getHttpBin()))
                //route everything to target service uri
                .route(predicateSpec -> predicateSpec
                        .path("/**")
                        .uri(targetServiceUri))
                .build();
    }
}
