package com.microservices.limits_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.microservices.limits_service.config.Config;
import com.microservices.limits_service.model.Limits;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/limits-service")
public class LimitsController {

    private final Config config;

    LimitsController(Config config) {
        this.config = config;
    }

    @GetMapping("/limits")
    public Mono<Limits> retriveLimits() {
        return Mono.fromSupplier(
                () -> new Limits(
                        config.min(), config.max()));
    }

}
