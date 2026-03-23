package com.ecommerce.orderservice.exception;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, int requested) {
        super("Stock insuffisant pour le produit ID " + productId +
              ". Quantité demandée: " + requested);
    }
}
