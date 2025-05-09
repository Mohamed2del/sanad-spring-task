package org.example.sanadspringtask.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rate_logs")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currencyCode;

    private double rate;

    private LocalDateTime timestamp;

    public ExchangeRateLog(String currencyCode, double rate, LocalDateTime timestamp) {
        this.currencyCode = currencyCode;
        this.rate = rate;
        this.timestamp = timestamp;
    }

}