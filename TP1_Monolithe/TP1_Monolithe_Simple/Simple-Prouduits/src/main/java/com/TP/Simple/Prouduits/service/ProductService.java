package com.TP.Simple.Prouduits.service;
import com.TP.Simple.Prouduits.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.TP.Simple.Prouduits.entity.Product;
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository ProductRepository;
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return ProductRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return ProductRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Product not found with id: " + id));
    }
    public Product create(Product product) {
        return ProductRepository.save(product);
    }
    public Product update(Long id, Product details) {
        Product product = getById(id);
        product.setName(details.getName());
        product.setDescription(details.getDescription());
        product.setPrice(details.getPrice());
        product.setStock(details.getStock());
        return product; // save inutile si entité managed
    }
    public void delete(Long id) {
        Product product = getById(id);
        ProductRepository.delete(product);
    }
}
