package com.microservices.currency_coversion_service.controller;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ProblemDetail handleWebClientExceptions(WebClientResponseException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(ex.getStatusCode());
        problemDetail.setTitle("Upstream Service Error");
        problemDetail.setDetail(ex.getResponseBodyAsString());
        
        return problemDetail;
    }
}