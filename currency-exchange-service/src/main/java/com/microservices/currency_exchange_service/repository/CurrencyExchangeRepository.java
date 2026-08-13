package com.microservices.currency_exchange_service.repository;

import com.microservices.currency_exchange_service.model.entity.CurrencyExchange;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CurrencyExchangeRepository extends R2dbcRepository<CurrencyExchange, Long> {

    Mono<CurrencyExchange> findByFromAndTo(String from, String to);
}
