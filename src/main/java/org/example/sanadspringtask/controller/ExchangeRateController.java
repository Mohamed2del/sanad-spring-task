package org.example.sanadspringtask.controller;

import org.example.sanadspringtask.cache.ExchangeRateCache;
import org.example.sanadspringtask.dto.ExchangeRateResponse;
import org.example.sanadspringtask.dto.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/rates")
@Tag(name = "Exchange Rates", description = "Retrieve latest exchange rates for tracked currencies")
public class ExchangeRateController {

    private final ExchangeRateCache exchangeRateCache;

    public ExchangeRateController(ExchangeRateCache exchangeRateCache) {
        this.exchangeRateCache = exchangeRateCache;
    }

    @Operation(summary = "Get exchange rate for a specific currency", description = "Returns the latest exchange rate for the given currency code (e.g., EGP, EUR, SAR) based on USD")
    @GetMapping("/{currency}")
    public ResponseEntity<GenericResponse<ExchangeRateResponse>> getRate(@PathVariable String currency) {
        String upperCaseCurrency = currency.toUpperCase();
        Double rate = exchangeRateCache.getRate(upperCaseCurrency);
        
        if (rate == null) {
            // Check if the currency exists in the database but has no rate yet
            boolean isTrackedCurrency = exchangeRateCache.isTrackedCurrency(upperCaseCurrency);
            String message = isTrackedCurrency 
                ? "Exchange rate not available yet. Please try again later."
                : "Currency code not found or not supported.";
            
            return ResponseEntity.status(404)
                    .body(new GenericResponse<ExchangeRateResponse>(404, message, null));
        }
        
        ExchangeRateResponse response = new ExchangeRateResponse(upperCaseCurrency, rate);
        return ResponseEntity.ok(new GenericResponse<ExchangeRateResponse>(200, "Success", response));
    }
}