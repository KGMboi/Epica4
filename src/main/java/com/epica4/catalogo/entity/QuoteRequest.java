package com.epica4.catalogo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "quote_requests")
public class QuoteRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 120)
    private String customerName;

    @Column(nullable = false, length = 254)
    private String customerEmail;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 1000)
    private String details;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    protected QuoteRequest() {
    }

    public QuoteRequest(
            Product product,
            String customerName,
            String customerEmail,
            int quantity,
            String details,
            LocalDateTime requestedAt) {
        this.product = product;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.quantity = quantity;
        this.details = details;
        this.status = "PENDING";
        this.requestedAt = requestedAt;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }
}
