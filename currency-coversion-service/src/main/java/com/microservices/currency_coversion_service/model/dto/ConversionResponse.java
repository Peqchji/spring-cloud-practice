package com.microservices.currency_coversion_service.model.dto;

import java.math.BigDecimal;

public record ConversionResponse(
    Long id,
    String from,
    String to,
    BigDecimal quantity,
    BigDecimal totalCalculatedAmount,
    String environment
) {}
