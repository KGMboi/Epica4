package com.epica4.catalogo.dto;

import com.epica4.catalogo.entity.QuoteRequest;

import java.time.LocalDateTime;

public record QuoteResponse(
        Long id,
        Long productId,
        String productName,
        String customerName,
        String customerEmail,
        int quantity,
        String details,
        String status,
        LocalDateTime requestedAt) {

    public static QuoteResponse from(QuoteRequest quoteRequest) {
        return new QuoteResponse(
                quoteRequest.getId(),
                quoteRequest.getProduct().getId(),
                quoteRequest.getProduct().getName(),
                quoteRequest.getCustomerName(),
                quoteRequest.getCustomerEmail(),
                quoteRequest.getQuantity(),
                quoteRequest.getDetails(),
                quoteRequest.getStatus(),
                quoteRequest.getRequestedAt());
    }
}
