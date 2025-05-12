package org.example.sanadspringtask.java.org.example.sanadspringtask.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import org.example.sanadspringtask.cache.ExchangeRateCache;
import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.repository.CurrencyRepository;
import org.example.sanadspringtask.repository.ExchangeRateLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.example.sanadspringtask.scheduler.ExchangeRateScheduler;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.flyway.enabled=false")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
class ExchangeRateSchedulerTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateLogRepository logRepository;

    @Autowired
    private ExchangeRateCache cache;

    @Autowired
    private ExchangeRateScheduler scheduler;

    @BeforeEach
    void setUp() {
        currencyRepository.deleteAll();
        logRepository.deleteAll();
        cache.getAllUsdRates().clear(); // renamed method in cache
    }

    @Test
    void shouldCallApiAndStoreData() {
        currencyRepository.save(new Currency("EGP"));

        scheduler.fetchRates();

        // Check USD rate map has many entries
        assertThat(cache.getAllUsdRates()).isNotEmpty();
        assertThat(cache.getAllUsdRates()).containsKey("EGP");

        // Check that tracked currency was logged
        assertThat(logRepository.count()).isGreaterThan(0);
    }
}
