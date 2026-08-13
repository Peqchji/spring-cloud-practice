package com.microservices.currency_exchange_service.model.dto;

import java.math.BigDecimal;

public record CurrencyExchangeResponse(
        Long id,
        String from,
        String to,
        BigDecimal conversionMultiple,
        String environment) {

}
