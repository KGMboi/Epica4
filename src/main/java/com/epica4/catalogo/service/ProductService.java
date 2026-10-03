package com.epica4.catalogo.service;

import com.epica4.catalogo.dto.CreateProductRequest;
import com.epica4.catalogo.dto.ProductResponse;
import com.epica4.catalogo.entity.Product;
import com.epica4.catalogo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse create(CreateProductRequest request) {
        Product product = new Product(
                request.name().trim(),
                request.description().trim(),
                request.category().trim(),
                request.basePrice());
        return ProductResponse.from(productRepository.save(product));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAllByOrderByIdAsc().stream()
                .map(ProductResponse::from)
                .toList();
    }
}
