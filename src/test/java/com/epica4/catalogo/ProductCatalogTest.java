package com.epica4.catalogo;

import com.epica4.catalogo.entity.Product;
import com.epica4.catalogo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductCatalogTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void clearCatalog() {
        productRepository.deleteAll();
    }

    @Test
    void returnsEmptyArrayWhenCatalogIsEmpty() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void returnsAllProductsWithTheirCatalogFields() throws Exception {
        productRepository.saveAll(List.of(
                new Product("Laptop", "Laptop para oficina", "Computo", new BigDecimal("12500.00")),
                new Product("Mouse", "Mouse inalambrico", "Accesorios", new BigDecimal("599.50"))));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].description").value("Laptop para oficina"))
                .andExpect(jsonPath("$[0].category").value("Computo"))
                .andExpect(jsonPath("$[0].basePrice").value(12500.00))
                .andExpect(jsonPath("$[1].name").value("Mouse"))
                .andExpect(jsonPath("$[1].basePrice").value(599.50));
    }
}