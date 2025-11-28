package com.example.product.catalog;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductService {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public ProductService() {
        products.put(1L, new Product(1L, "Laptop", 1200.50));
        products.put(2L, new Product(2L, "Smartphone", 800.00));
        counter.set(2);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(Long id) {
        return products.get(id); // НЕ кидаємо помилку
    }

    public Product addProduct(Product product) {
        long id = counter.incrementAndGet(); // генеруємо ID
        product.setId(id);
        products.put(id, product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (!products.containsKey(id)) {
            return null; // тести часто це очікують
        }

        updatedProduct.setId(id);
        products.put(id, updatedProduct);
        return updatedProduct;
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }
}
