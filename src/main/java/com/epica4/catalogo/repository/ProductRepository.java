package com.epica4.catalogo.repository;

import com.epica4.catalogo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
