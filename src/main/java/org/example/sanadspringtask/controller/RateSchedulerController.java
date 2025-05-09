package org.example.sanadspringtask.controller;

import org.example.sanadspringtask.scheduler.ExchangeRateScheduler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.sanadspringtask.dto.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/scheduler")
@Tag(name = "Rate Scheduler", description = "API for managing exchange rate updates")
public class RateSchedulerController {

    private final ExchangeRateScheduler exchangeRateScheduler;

    public RateSchedulerController(ExchangeRateScheduler exchangeRateScheduler) {
        this.exchangeRateScheduler = exchangeRateScheduler;
    }

    @Operation(summary = "Trigger immediate exchange rate update", 
        description = "Manually triggers an update of exchange rates from the external service. This operation bypasses the normal scheduled update interval.")
    @GetMapping("/trigger")
    public ResponseEntity<?> triggerFetchRates() {
        exchangeRateScheduler.fetchRates();
        return ResponseEntity.ok(new GenericResponse<>(200, "Exchange rates fetch triggered", null));
    }
}
