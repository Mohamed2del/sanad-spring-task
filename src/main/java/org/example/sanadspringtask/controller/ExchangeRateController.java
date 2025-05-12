package org.example.sanadspringtask.controller;

import org.example.sanadspringtask.dto.ExchangeRateResponse;
import org.example.sanadspringtask.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.sanadspringtask.service.ExchangeRateService;

@RestController
@RequestMapping("/api/rates")
@Tag(name = "Exchange Rates", description = "Retrieve latest exchange rates for tracked currencies")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping("/{currency}")
    @Operation(summary = "Get exchange rates from a given currency")
    public ExchangeRateResponse getRate(@PathVariable String currency) {
        return exchangeRateService.getRatesFromCurrency(currency);
    }

}