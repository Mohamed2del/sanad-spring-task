package org.example.sanadspringtask.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ExchangeRateCache {
    private final Map<String, Double> rateMap = new ConcurrentHashMap<>();

    public void updateRate(String currencyCode, Double rate) {
        rateMap.put(currencyCode, rate);
    }

    public Double getRate(String currencyCode) {
        return rateMap.get(currencyCode);
    }

    public Map<String, Double> getAllRates() {
        return rateMap;
    }
}