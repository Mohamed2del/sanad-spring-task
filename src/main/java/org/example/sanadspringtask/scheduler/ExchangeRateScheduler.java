package org.example.sanadspringtask.scheduler;
import org.example.sanadspringtask.cache.ExchangeRateCache;
import org.example.sanadspringtask.configuration.OpenExchangeRatesConfig;
import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.model.ExchangeRateLog;
import org.example.sanadspringtask.repository.CurrencyRepository;
import org.example.sanadspringtask.repository.ExchangeRateLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ExchangeRateScheduler {

    private final CurrencyRepository currencyRepository;
    private final ExchangeRateCache exchangeRateCache;
    private final RestTemplate restTemplate;
    private final OpenExchangeRatesConfig config;
    private final ExchangeRateLogRepository exchangeRateLogRepository;

    public ExchangeRateScheduler(CurrencyRepository currencyRepository, ExchangeRateCache exchangeRateCache, OpenExchangeRatesConfig config, ExchangeRateLogRepository exchangeRateLogRepository) {
        this.currencyRepository = currencyRepository;
        this.exchangeRateCache = exchangeRateCache;
        this.config = config;
        this.exchangeRateLogRepository = exchangeRateLogRepository;
        this.restTemplate = new RestTemplate();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        fetchRates();
    }

    @Scheduled(fixedRate = 3600000 ) // Every hour
    public void fetchRates() {
        String url = "https://openexchangerates.org/api/latest.json?app_id=" + config.getAppId();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");

        for (Currency currency : currencyRepository.findAll()) {
            String code = currency.getCode();
            if (rates.containsKey(code)) {
                Number rawRate = rates.get(code);
                double rate = rawRate.doubleValue();
                exchangeRateCache.updateRate(code, rate);
                ExchangeRateLog log = new ExchangeRateLog(code, rate, LocalDateTime.now());
                exchangeRateLogRepository.save(log);
            }
        }
    }
}