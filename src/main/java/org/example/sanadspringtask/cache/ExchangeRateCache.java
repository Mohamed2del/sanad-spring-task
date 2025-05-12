package org.example.sanadspringtask.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ExchangeRateCache {
    private final Map<String, Double> usdRatesMap = new ConcurrentHashMap<>();
    private final Set<String> trackedCurrencies = ConcurrentHashMap.newKeySet();

    public void updateUsdRate(String currencyCode, Double rate) {
        usdRatesMap.put(currencyCode.toUpperCase(), rate);
    }

    public Double getUsdRate(String currencyCode) {
        return usdRatesMap.get(currencyCode.toUpperCase());
    }

    public Map<String, Double> getAllUsdRates() {
        return usdRatesMap;
    }

    public void addTrackedCurrency(String currencyCode) {
        trackedCurrencies.add(currencyCode.toUpperCase());
    }

    public boolean isTrackedCurrency(String currencyCode) {
        return trackedCurrencies.contains(currencyCode.toUpperCase());
    }
}
