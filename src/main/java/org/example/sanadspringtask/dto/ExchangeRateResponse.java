package org.example.sanadspringtask.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRateResponse {
    private String currency;
    private double rate;
    private String base = "USD";
    private LocalDateTime timestamp = LocalDateTime.now();

    public ExchangeRateResponse(String currency, double rate) {
        this.currency = currency;
        this.rate = rate;
    }


}