package com.epica4.catalogo.service;

import com.epica4.catalogo.dto.CreateQuoteRequest;
import com.epica4.catalogo.dto.QuoteResponse;
import com.epica4.catalogo.entity.Product;
import com.epica4.catalogo.entity.QuoteRequest;
import com.epica4.catalogo.exception.ResourceNotFoundException;
import com.epica4.catalogo.repository.ProductRepository;
import com.epica4.catalogo.repository.QuoteRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class QuoteRequestService {

    private final ProductRepository productRepository;
    private final QuoteRequestRepository quoteRequestRepository;

    public QuoteRequestService(
            ProductRepository productRepository,
            QuoteRequestRepository quoteRequestRepository) {
        this.productRepository = productRepository;
        this.quoteRequestRepository = quoteRequestRepository;
    }

    @Transactional
    public QuoteResponse create(CreateQuoteRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el producto con ID " + request.productId()));
        QuoteRequest quoteRequest = new QuoteRequest(
                product,
                request.customerName().trim(),
                request.customerEmail().trim(),
                request.quantity(),
                request.details() == null ? null : request.details().trim(),
                LocalDateTime.now());
        return QuoteResponse.from(quoteRequestRepository.save(quoteRequest));
    }
}
