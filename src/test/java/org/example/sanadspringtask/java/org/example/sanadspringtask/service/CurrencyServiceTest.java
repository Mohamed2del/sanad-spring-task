package org.example.sanadspringtask.java.org.example.sanadspringtask.service;

import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.example.sanadspringtask.service.CurrencyService;
import org.example.sanadspringtask.service.ExchangeRateService;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldAddNewCurrency() {
        String code = "EGP";
        when(currencyRepository.findByCode(code)).thenReturn(Optional.empty());
        when(currencyRepository.save(any())).thenReturn(new Currency(code));

        Currency saved = currencyService.addCurrency(code);

        assertEquals("EGP", saved.getCode());
        verify(currencyRepository).save(any(Currency.class));
        verify(exchangeRateService).fetchAndProcessRates();
    }

    @Test
    void shouldThrowExceptionIfCurrencyExists() {
        String code = "USD";
        when(currencyRepository.findByCode(code)).thenReturn(Optional.of(new Currency(code)));

        assertThrows(IllegalArgumentException.class, () -> currencyService.addCurrency(code));
        verify(currencyRepository, never()).save(any());
        verify(exchangeRateService, never()).fetchAndProcessRates();
    }
}