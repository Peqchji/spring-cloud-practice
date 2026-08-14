package com.microservices.currency_exchange_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
// import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
// import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
// import io.github.resilience4j.retry.annotation.Retry;
import reactor.core.publisher.Mono;

@RestController
public class CircuitBreakerController {

    private final WebClient webClient = WebClient.create();
    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
    // @Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
    // @CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
    @Bulkhead(name = "default", fallbackMethod = "ratelimit")
    public Mono<String> sampleApi() {
        return webClient.get()
                .uri("http://localhost:8080/some-api")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> logger.info("Error happened"));
    }

    public Mono<String> hardcodedResponse(Throwable ex) {
        return Mono.just("Fallback response because localhost:8080 is down");
    }

        public Mono<String> bulkhead(Throwable ex) {
        return Mono.just("Bulkhead response because localhost:8080 is down");
    }
}
