package org.example.sanadspringtask.service;

import java.util.List;

import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    public Currency addCurrency(String code) {
        if (currencyRepository.findByCode(code).isPresent()) {
            throw new IllegalArgumentException("Currency code already exists: " + code);
        }
        return currencyRepository.save(new Currency(code));
    }
    
    
}
