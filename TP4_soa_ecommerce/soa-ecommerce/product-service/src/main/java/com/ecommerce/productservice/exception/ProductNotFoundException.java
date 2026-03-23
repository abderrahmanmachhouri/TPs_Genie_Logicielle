package com.ecommerce.productservice.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Produit introuvable avec l'ID : " + id);
    }
}
