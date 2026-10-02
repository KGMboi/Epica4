package com.epica4.catalogo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registersProductWithRequiredFields() throws Exception {
        mockMvc.perform(post("/api/products")
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
                .andExpect(jsonPath("$.description").value("Laptop para oficina"))
                .andExpect(jsonPath("$.category").value("Computo"))
                .andExpect(jsonPath("$.basePrice").value(12500.00));
    }

    @Test
    void rejectsProductWithBlankNameOrNonPositivePrice() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": " ",
                                  "description": "Laptop para oficina",
                                  "category": "Computo",
                                  "basePrice": 0
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
