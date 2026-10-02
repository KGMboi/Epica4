package com.epica4.catalogo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 1000) String description,
        @NotBlank @Size(max = 80) String category,
        @NotNull @DecimalMin(value = "0.01") BigDecimal basePrice) {
}
