package com.ecommerce.orderservice.client;

import com.ecommerce.orderservice.dto.ProductDTO;
import com.ecommerce.orderservice.exception.ProductServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * Client Feign pour communiquer avec Product Service via Eureka
 * Exercice 3 : fallback = ProductClientFallback.class pour gérer les pannes
 */
@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);

    /**
     * Exercice 1 : Appel pour mettre à jour le stock après une commande
     */
    @PutMapping("/api/products/{id}/stock")
    ProductDTO updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> body);
}
