package org.example.sanadspringtask.controller;

import java.util.List;

import org.example.sanadspringtask.dto.AddCurrencyRequest;
import org.example.sanadspringtask.dto.GenericResponse;
import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/currencies")
@Tag(name = "Currencies", description = "Manage currencies to fetch exchange rates")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get all added currencies")
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @Operation(summary = "Add a new currency to track")
    @PostMapping
    public Currency addCurrency(@Valid @RequestBody AddCurrencyRequest request) {
        return currencyService.addCurrency(request.code());
    }

}