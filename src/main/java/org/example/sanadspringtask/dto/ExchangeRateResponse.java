package org.example.sanadspringtask.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRateResponse {
    private String name;
    private Map<String, Double> rates;

    public ExchangeRateResponse(String name, Map<String, Double> rates) {
        this.name = name;
        this.rates = rates;
    }
}
