package com.microservices.limit_service.model;

/**
 * InnerLimits
 */
public record Limits(
    int min,
    int max
) {
}
