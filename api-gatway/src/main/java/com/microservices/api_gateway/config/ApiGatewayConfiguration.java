package com.microservices.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/currency-exchange/**")
                        .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-proxy/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-fallback/**")
                        .filters(f -> f.rewritePath(
                            "/currency-conversion-fallback/(?<segment>.*)",
                            "/currency-conversion-proxy/${segment}"
                        ))
                        .uri("lb://currency-conversion"))
                .build();
    }

}
