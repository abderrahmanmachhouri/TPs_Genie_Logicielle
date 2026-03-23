package com.ecommerce.productservice.service;

import com.ecommerce.productservice.exception.InsufficientStockException;
import com.ecommerce.productservice.exception.ProductNotFoundException;
import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        log.info("Récupération de tous les produits");
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        log.info("Récupération du produit avec ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        log.info("Création du produit: {}", product.getName());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        log.info("Mise à jour du produit avec ID: {}", id);
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        log.info("Suppression du produit avec ID: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    /**
     * Exercice 1 : Met à jour le stock d'un produit après une commande
     * Décrémente le stock de la quantité commandée
     */
    @Transactional
    public Product updateStock(Long id, Integer quantityToReduce) {
        log.info("Mise à jour du stock du produit ID: {}, quantité à réduire: {}", id, quantityToReduce);
        Product product = getProductById(id);

        if (product.getStock() < quantityToReduce) {
            throw new InsufficientStockException(id, quantityToReduce, product.getStock());
        }

        product.setStock(product.getStock() - quantityToReduce);
        Product savedProduct = productRepository.save(product);
        log.info("Stock mis à jour: produit {} - nouveau stock: {}", product.getName(), savedProduct.getStock());
        return savedProduct;
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }
}
