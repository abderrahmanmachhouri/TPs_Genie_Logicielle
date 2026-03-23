package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Exercice 3 : Fallback utilisé quand Product Service est indisponible
 * Implémente ProductClient et retourne des réponses par défaut
 */
@Component
@Slf4j
public class ProductClientFallback implements ProductClient {

    @Override
    public ProductDTO getProductById(Long id) {
        log.error("Product Service indisponible - fallback pour getProductById({})", id);
        return null; // null sera géré dans OrderService pour lever une exception explicite
    }

    @Override
    public ProductDTO updateStock(Long id, Map<String, Integer> body) {
        log.error("Product Service indisponible - fallback pour updateStock({})", id);
        return null;
    }
}
