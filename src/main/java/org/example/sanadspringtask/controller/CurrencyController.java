package org.example.sanadspringtask.controller;
import java.util.List;

import org.example.sanadspringtask.dto.GenericResponse;
import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/currencies")
@Tag(name = "Currencies", description = "Manage currencies to fetch exchange rates")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get all added currencies")
    @GetMapping
    public ResponseEntity<GenericResponse<List<Currency>>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        return ResponseEntity.ok(new GenericResponse<>(200,"Success", currencies));
    }

    @Operation(summary = "Add a new currency to track")
    @PostMapping
    public ResponseEntity<GenericResponse<?>> addCurrency(@RequestParam String code) {
        try {
            Currency currency = currencyService.addCurrency(code.toUpperCase());
            return ResponseEntity.ok(new GenericResponse<>(200,"Success", currency));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new GenericResponse<>(400,"Error", e.getMessage()));
        }
    }
    
}