package com.microservices.currency_coversion_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.microservices.currency_coversion_service.proxy.CurrencyExchangeProxy;

@Configuration
public class HttpExchangeConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public CurrencyExchangeProxy currencyExchangeProxy(WebClient.Builder builder) {
        WebClient webClient = builder
                .baseUrl("http://currency-exchange")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(
                WebClientAdapter.create(webClient)).build();

        return factory.createClient(CurrencyExchangeProxy.class);
    }
}
