package com.epica4.catalogo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateQuoteRequest(
        @Positive long productId,
        @NotBlank @Size(max = 120) String customerName,
        @NotBlank @Email @Size(max = 254) String customerEmail,
        @Positive int quantity,
        @Size(max = 1000) String details) {
}
