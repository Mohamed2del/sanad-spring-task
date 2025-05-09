package org.example.sanadspringtask.java.org.example.sanadspringtask.controller;

import org.example.sanadspringtask.model.Currency;
import org.example.sanadspringtask.repository.CurrencyRepository;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(properties = "spring.flyway.enabled=false")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
class CurrencyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        currencyRepository.deleteAll(); // Ensure isolation
    }

    @Test
    void shouldAddNewCurrency() throws Exception {
        mockMvc.perform(post("/api/currencies?code=EGP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.result.code").value("EGP"));
    }

    @Test
    void shouldRejectDuplicateCurrency() throws Exception {
        currencyRepository.save(new Currency("USD"));

        mockMvc.perform(post("/api/currencies?code=USD"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.result", containsString("already exists")));
    }

    @Test
    void shouldListAllCurrencies() throws Exception {
        currencyRepository.save(new Currency("SAR"));
        currencyRepository.save(new Currency("EUR"));

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[*].code", containsInAnyOrder("SAR", "EUR")));
    }
}