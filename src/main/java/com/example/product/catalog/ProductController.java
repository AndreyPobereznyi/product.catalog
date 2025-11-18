package com.example.product.catalog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public ProductController() {
        products.put(counter.incrementAndGet(),
                new Product(1L, "Laptop", 1200.50, LocalDate.now().plusDays(5)));

        products.put(counter.incrementAndGet(),
                new Product(2L, "Smartphone", 800.00, LocalDate.now().plusDays(5)));
    }

    // GET /products - отримати всі продукти
    @GetMapping
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    // GET /products/{id} - отримати продукт за ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        Product product = products.get(id);
        if (product!= null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /products - створити новий продукт
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product.getDueDate() == null) {
            product.setDueDate(LocalDate.now().plusDays(5));
        }

        long newId = counter.incrementAndGet();
        product.setId(newId);
        products.put(newId, product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);

    }

    // PUT /products/{id} - оновити існуючий продукт
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product updatedProduct) {
        if (!products.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        if (updatedProduct.getDueDate() == null) {
            updatedProduct.setDueDate(LocalDate.now().plusDays(5));
        }

        updatedProduct.setId(id);
        products.put(id, updatedProduct);

        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE /products/{id} - видалити продукт
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id) {
        if (products.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
