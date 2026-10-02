package com.epica4.catalogo.repository;

import com.epica4.catalogo.entity.QuoteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRequestRepository extends JpaRepository<QuoteRequest, Long> {
}
