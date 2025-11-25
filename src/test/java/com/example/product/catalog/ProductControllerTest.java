package com.example.product.catalog;

import com.example.product.catalog.Product;
import com.example.product.catalog.ProductController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturnProductByIdWhenFound() throws Exception {
// Act & Assert
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldReturnNotFoundWhenProductNotFound() throws
            Exception {
// Act & Assert
        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewProduct() throws Exception {
// Arrange
        Product savedProduct = new Product(4L, "Keyboard", 75.00, LocalDate.now().plusDays(5));
// Act & Assert
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Keyboard"));
    }

}