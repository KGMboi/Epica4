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
    void registersProductAndTrimsTextFields() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "  Laptop  ",
                                  "description": "  Laptop para oficina  ",
                                  "category": "  Computo  ",
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
    void rejectsBlankRequiredFields() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": " ",
                                  "description": "",
                                  "category": " ",
                                  "basePrice": 12500.00
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsZeroPrice() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Laptop",
                                  "description": "Laptop para oficina",
                                  "category": "Computo",
                                  "basePrice": 0
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsPriceWithMoreThanTwoDecimalPlaces() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Laptop",
                                  "description": "Laptop para oficina",
                                  "category": "Computo",
                                  "basePrice": 12500.123
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rejectsPriceExceedingDatabasePrecision() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Laptop",
                                  "description": "Laptop para oficina",
                                  "category": "Computo",
                                  "basePrice": 10000000000.00
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
