package com.microservices.limits_service.model;

/**
 * InnerLimits
 */
public record Limits(
    int min,
    int max
) {
}
