package com.microservices.currency_coversion_service.proxy;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import com.microservices.currency_coversion_service.model.dto.CurrencyExchangeResponse;

public interface CurrencyExchangeProxy {
    
    @GetExchange("/currency-exchange/from/{from}/to/{to}")
    Mono<CurrencyExchangeResponse> retrieveExchangeValue(
        @PathVariable("from") String from, 
        @PathVariable("to") String to
    );
}
