package com.epica4.catalogo.dto;

import com.epica4.catalogo.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String category,
        BigDecimal basePrice) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getBasePrice());
    }
}
