package org.example.sanadspringtask.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.sanadspringtask.cache.ExchangeRateCache;
import org.example.sanadspringtask.configuration.OpenExchangeRatesConfig;
import org.example.sanadspringtask.dto.ExchangeRateResponse;
import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.model.ExchangeRateLog;
import org.example.sanadspringtask.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.example.sanadspringtask.repository.ExchangeRateLogRepository;

@Service
public class ExchangeRateService {

    private final WebClient webClient;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateLogRepository exchangeRateLogRepository;
    private final ExchangeRateCache exchangeRateCache;
    private final OpenExchangeRatesConfig config;

    public ExchangeRateService(WebClient webClient,
            CurrencyRepository currencyRepository,
            ExchangeRateLogRepository exchangeRateLogRepository,
            ExchangeRateCache exchangeRateCache,
            OpenExchangeRatesConfig config) {
        this.webClient = webClient;
        this.currencyRepository = currencyRepository;
        this.exchangeRateLogRepository = exchangeRateLogRepository;
        this.exchangeRateCache = exchangeRateCache;
        this.config = config;
    }

    public void fetchAndProcessRates() {
        String url = "https://openexchangerates.org/api/latest.json?app_id=" + config.getAppId();

        Map<String, Object> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        if (response == null || !response.containsKey("rates")) {
            return;
        }

        Map<String, Object> rawRates = (Map<String, Object>) response.get("rates");
        Map<String, Double> rates = new HashMap<>();

        rawRates.forEach((key, value) -> {
            if (value instanceof Number) {
                rates.put(key, ((Number) value).doubleValue());
            }
        });

        LocalDateTime timestamp = LocalDateTime.now();

        rates.forEach((code, rate) -> exchangeRateCache.updateUsdRate(code, rate));

        // Log only user-tracked (added) currencies [not sure if this is required but
        // its better to not save all currencies]
        List<Currency> trackedCurrencies = currencyRepository.findAll();
        trackedCurrencies.forEach(currency -> {
            String code = currency.getCode();
            exchangeRateCache.addTrackedCurrency(code); // Only mark these as tracked
            Double rate = rates.get(code);
            if (rate != null) {
                ExchangeRateLog log = new ExchangeRateLog(code, rate, timestamp);
                exchangeRateLogRepository.save(log);
            }
        });
    }

    public ExchangeRateResponse getRatesFromCurrency(String currencyCode) {
        String baseCurrency = currencyCode.toUpperCase();
        Double baseToUsdRate = exchangeRateCache.getUsdRate(baseCurrency);

        if (baseToUsdRate == null) {
            throw new RuntimeException("Currency code not found or not supported.");
        }

        Map<String, Double> usdRates = exchangeRateCache.getAllUsdRates();
        Map<String, Double> convertedRates = new HashMap<>();

        usdRates.forEach((targetCurrency, usdToTargetRate) -> {
            if (!targetCurrency.equalsIgnoreCase(baseCurrency)) {
                double converted = usdToTargetRate / baseToUsdRate;
                double formatted = BigDecimal.valueOf(converted)
                        .setScale(2, RoundingMode.DOWN)
                        .doubleValue();
                convertedRates.put(targetCurrency, formatted);
            }
        });

        return new ExchangeRateResponse(baseCurrency, convertedRates);
    }

}
