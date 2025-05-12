package org.example.sanadspringtask.scheduler;

import org.example.sanadspringtask.service.ExchangeRateService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRateScheduler {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateScheduler(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        fetchRates();
    }

    @Scheduled(fixedRate = 3600000)
    public void fetchRates() {
        exchangeRateService.fetchAndProcessRates();
    }
}