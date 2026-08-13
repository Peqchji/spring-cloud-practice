package com.microservices.limits_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("limits-service")
public record Config(
    int min,
    int max
) {
}
