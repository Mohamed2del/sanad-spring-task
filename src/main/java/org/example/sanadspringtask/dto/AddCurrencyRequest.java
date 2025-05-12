package org.example.sanadspringtask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddCurrencyRequest(
        @NotBlank(message = "Currency code is required") @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be 3 uppercase letters (e.g., USD, SAR)") String code) {
}
