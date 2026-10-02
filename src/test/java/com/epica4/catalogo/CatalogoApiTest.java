package com.epica4.catalogo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatalogoApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsAndListsProductThenAcceptsQuoteRequest() throws Exception {
        String productLocation = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Laptop",
                                  "description": "Laptop para oficina",
                                  "category": "Computo",
                                  "basePrice": 12500.00
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        long productId = Long.parseLong(productLocation.substring(productLocation.lastIndexOf('/') + 1));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"));

        mockMvc.perform(post("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": %d,
                                  "customerName": "Ana Perez",
                                  "customerEmail": "ana@example.com",
                                  "quantity": 3,
                                  "details": "Entrega en sucursal"
                                }
                                """.formatted(productId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(productId))
                .andExpect(jsonPath("$.productName").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(3))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void rejectsQuoteForUnknownProduct() throws Exception {
        mockMvc.perform(post("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": 999999,
                                  "customerName": "Ana Perez",
                                  "customerEmail": "ana@example.com",
                                  "quantity": 1
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No existe el producto con ID 999999"));
    }

    @Test
    void rejectsInvalidProductInput() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": " ",
                                  "description": "Producto",
                                  "category": "General",
                                  "basePrice": 0
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
