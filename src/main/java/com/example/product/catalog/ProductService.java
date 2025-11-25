package com.example.product.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductService {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public ProductService() {
        // Початкові дані для демонстрації
        products.put(counter.incrementAndGet(), new Product(1L, "Laptop", 1200.50));
        products.put(counter.incrementAndGet(), new Product(2L, "Smartphone", 800.00));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}