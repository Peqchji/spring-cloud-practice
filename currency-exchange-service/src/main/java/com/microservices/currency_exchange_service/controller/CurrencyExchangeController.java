package com.microservices.currency_exchange_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.microservices.currency_exchange_service.model.dto.CurrencyExchangeResponse;
import com.microservices.currency_exchange_service.repository.CurrencyExchangeRepository;

import reactor.core.publisher.Mono;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/currency-exchange")
public class CurrencyExchangeController {

    private final Environment env;
    private final CurrencyExchangeRepository currencyExchangeRepository;

    CurrencyExchangeController(Environment env, CurrencyExchangeRepository currencyExchangeRepository) {
        this.env = env;
        this.currencyExchangeRepository = currencyExchangeRepository;
    }

    @GetMapping("/from/{from}/to/{to}")
    public Mono<CurrencyExchangeResponse> retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to) {
        return currencyExchangeRepository.findByFromAndTo(from, to)
                .switchIfEmpty(
                        Mono.error(() -> new RuntimeException(
                                String.format("Currency from %s to %s detail not found", from, to))))
                .map(currencyExchange -> new CurrencyExchangeResponse(
                        currencyExchange.getId(),
                        currencyExchange.getFrom(),
                        currencyExchange.getTo(),
                        currencyExchange.getConversionMultiple(),
                        env.getProperty("local.server.port")));
    }

}
