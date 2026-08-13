package com.microservices.currency_coversion_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import com.microservices.currency_coversion_service.model.dto.ConversionResponse;
import com.microservices.currency_coversion_service.model.dto.CurrencyExchangeResponse;
import com.microservices.currency_coversion_service.proxy.CurrencyExchangeProxy;
import com.microservices.currency_coversion_service.proxy.CurrencyExchangeFeignProxy;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.function.Function;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class CurrencyConversionController {

        private final CurrencyExchangeProxy proxy;
        private final CurrencyExchangeFeignProxy feignProxy;
        private final Environment env;

        private final WebClient webClient = WebClient.builder()
                        .baseUrl("http://localhost:8000")
                        .build();

        CurrencyConversionController(
                        Environment env,
                        CurrencyExchangeProxy proxy,
                        CurrencyExchangeFeignProxy feignProxy) {

                this.env = env;
                this.proxy = proxy;
                this.feignProxy = feignProxy;
        }

        @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
        public Mono<ConversionResponse> convertCurrency(
                        @PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                return this.webClient.get()
                                .uri("/currency-exchange/from/{from}/to/{to}", from, to)
                                .retrieve()
                                .bodyToMono(CurrencyExchangeResponse.class)
                                .transform(this.mapConversionResponse(quantity));
        }

        @GetMapping("/currency-conversion-proxy/from/{from}/to/{to}/quantity/{quantity}")
        public Mono<ConversionResponse> convertCurrencyFeign(
                        @PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                return proxy.retrieveExchangeValue(from, to)
                                .transform(this.mapConversionResponse(quantity));
        }

        @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
        public Mono<ConversionResponse> convertCurrencyFeignPoc(
                        @PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                return Mono.fromCallable(() -> feignProxy.retrieveExchangeValue(from, to))
                                .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())
                                .transform(this.mapConversionResponse(quantity));
        }

        private Function<Mono<CurrencyExchangeResponse>, Mono<ConversionResponse>> mapConversionResponse(
                        BigDecimal quantity) {
                return mono -> mono.map(resp -> new ConversionResponse(
                                resp.id(),
                                resp.from(),
                                resp.to(),
                                quantity,
                                quantity.multiply(
                                                resp.conversionMultiple()),
                               resp.environment()));
        }
}
