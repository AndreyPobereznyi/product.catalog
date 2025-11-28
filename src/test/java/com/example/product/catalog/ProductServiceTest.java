package com.example.product.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    public ProductService productService;

    @Test
    void testCreateProduct_Success() {
        Product p = new Product(1L, "Apple", 10.0);
        Product created = productService.addProduct(p);
        assertEquals("Apple", created.getName());
    }

    @Test
    void testCreateProduct_Fail_DuplicateId() {
        Product p = new Product(1L, "Apple", 10.0);
        productService.addProduct(p);
        assertThrows(RuntimeException.class,
                () -> productService.addProduct(p));
    }

    @Test
    void testGetProductById_Success() {
        Product p = new Product(2L, "Banana", 5.0);
        productService.addProduct(p);

        Product result = productService.getProductById(2L);
        assertEquals("Banana", result.getName());
    }

    @Test
    void testGetProductById_NotFound() {
        assertThrows(RuntimeException.class,
                () -> productService.getProductById(999L));
    }

    @Test
    void testUpdateProduct_Success() {
        Product p = new Product(3L, "Milk", 20.0);
        productService.addProduct(p);

        Product updated = new Product(3L, "Fresh Milk", 22.0);
        Product result = productService.updateProduct(3L, updated);

        assertEquals("Fresh Milk", result.getName());
    }

    @Test
    void testUpdateProduct_NotFound() {
        Product updated = new Product(999L, "X", 1.0);
        assertThrows(RuntimeException.class,
                () -> productService.updateProduct(999L, updated));
    }

    @Test
    void testDeleteProduct_Success() {
        Product p = new Product(4L, "Juice", 30.0);
        productService.addProduct(p);

        assertDoesNotThrow(() -> productService.deleteProduct(4L));
    }

    @Test
    void testDeleteProduct_NotFound() {
        assertThrows(RuntimeException.class,
                () -> productService.deleteProduct(999L));
    }
}