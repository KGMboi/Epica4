package com.epica4.catalogo.controller;

import com.epica4.catalogo.dto.CreateQuoteRequest;
import com.epica4.catalogo.dto.QuoteResponse;
import com.epica4.catalogo.service.QuoteRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/quotes")
public class QuoteRequestController {

    private final QuoteRequestService quoteRequestService;

    public QuoteRequestController(QuoteRequestService quoteRequestService) {
        this.quoteRequestService = quoteRequestService;
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> create(@Valid @RequestBody CreateQuoteRequest request) {
        QuoteResponse quote = quoteRequestService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(quote.id())
                .toUri();
        return ResponseEntity.created(location).body(quote);
    }
}
