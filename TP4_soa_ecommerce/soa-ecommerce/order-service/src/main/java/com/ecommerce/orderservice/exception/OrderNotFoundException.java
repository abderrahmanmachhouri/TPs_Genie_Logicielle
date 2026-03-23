package com.ecommerce.orderservice.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Commande introuvable avec l'ID : " + id);
    }
}
