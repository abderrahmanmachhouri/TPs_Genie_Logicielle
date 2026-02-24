package com.TP.Simple.Prouduits.service;

import com.TP.Simple.Prouduits.entity.Product;
import com.TP.Simple.Prouduits.repository.ProductRepository;
import com.TP.Simple.Prouduits.entity.Category;
import com.TP.Simple.Prouduits.repository.CategoryRepository;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Getter
@Setter
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    // CREATE avec Category
    public Product create(Product product) {

        if (product.getCategory() != null) {
            Category category = categoryRepository
                    .findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            product.setCategory(category);
        }

        return repository.save(product);
    }

    // UPDATE avec Category
    public Product update(Long id, Product details) {

        Product product = getById(id);

        product.setName(details.getName());
        product.setDescription(details.getDescription());
        product.setPrice(details.getPrice());
        product.setStock(details.getStock());

        if (details.getCategory() != null) {
            Category category = categoryRepository
                    .findById(details.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            product.setCategory(category);
        }

        return product;
    }

    public void delete(Long id) {
        Product product = getById(id);
        repository.delete(product);
    }
}