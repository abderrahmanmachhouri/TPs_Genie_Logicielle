package com.ecommerce.productservice.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, int requested, int available) {
        super("Stock insuffisant pour le produit ID " + productId +
              ". Demandé: " + requested + ", Disponible: " + available);
    }
}
