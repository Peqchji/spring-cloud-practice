package com.microservices.currency_coversion_service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.currency_coversion_service.model.dto.CurrencyExchangeResponse;

// For the POC, we explicitly define a different name so it doesn't conflict with the HTTP Exchange Proxy
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeFeignProxy {

    // Note: Returns the actual object, not a Mono!
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyExchangeResponse retrieveExchangeValue(
            @PathVariable("from") String from, 
            @PathVariable("to") String to);
}
