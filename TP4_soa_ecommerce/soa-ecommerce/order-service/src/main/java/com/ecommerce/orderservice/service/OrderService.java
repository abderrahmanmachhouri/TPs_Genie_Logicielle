package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.client.ProductClient;
import com.ecommerce.orderservice.dto.ProductDTO;
import com.ecommerce.orderservice.exception.InsufficientStockException;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.exception.ProductServiceException;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public List<Order> getAllOrders() {
        log.info("Récupération de toutes les commandes");
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Crée une commande :
     * 1. Vérifie l'existence du produit (Exercice 3 : gestion erreurs Feign)
     * 2. Vérifie le stock disponible (Exercice 1)
     * 3. Sauvegarde la commande
     * 4. Met à jour le stock du Product Service (Exercice 1)
     */
    @Transactional
    public Order createOrder(Long productId, Integer quantity) {
        log.info("Création d'une commande - produit: {}, quantité: {}", productId, quantity);

        // Exercice 3 : Récupérer le produit avec gestion des erreurs
        ProductDTO product = getProductSafely(productId);

        // Exercice 1 : Vérifier le stock avant de créer la commande
        if (product.getStock() < quantity) {
            throw new InsufficientStockException(productId, quantity);
        }

        // Construire et sauvegarder la commande
        Order order = new Order();
        order.setProductId(productId);
        order.setProductName(product.getName());
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);
        log.info("Commande créée avec ID: {}", savedOrder.getId());

        // Exercice 1 : Mettre à jour le stock dans Product Service
        updateProductStockSafely(productId, quantity, savedOrder.getId());

        // Confirmer la commande si le stock a été mis à jour
        savedOrder.setStatus("CONFIRMED");
        return orderRepository.save(savedOrder);
    }

    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        Order order = getOrderById(id);
        order.setStatus("CANCELLED");
        orderRepository.save(order);
        log.info("Commande {} annulée", id);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    /**
     * Exercice 3 : Récupère le produit avec gestion des erreurs du service
     */
    private ProductDTO getProductSafely(Long productId) {
        try {
            ProductDTO product = productClient.getProductById(productId);
            // Si le fallback retourne null, le service est indisponible
            if (product == null) {
                throw new ProductServiceException(
                        "Le service produit est indisponible. Veuillez réessayer plus tard.");
            }
            return product;
        } catch (FeignException.NotFound e) {
            log.error("Produit ID {} introuvable dans Product Service", productId);
            throw new feign.FeignException.NotFound(
                    "Produit introuvable avec l'ID : " + productId,
                    e.request(), null, null);
        } catch (FeignException e) {
            log.error("Erreur de communication avec Product Service: {}", e.getMessage());
            throw new ProductServiceException(
                    "Impossible de contacter le service produit.", e);
        }
    }

    /**
     * Exercice 1 + 3 : Met à jour le stock avec gestion des erreurs
     */
    private void updateProductStockSafely(Long productId, Integer quantity, Long orderId) {
        try {
            productClient.updateStock(productId, Map.of("quantity", quantity));
            log.info("Stock mis à jour pour le produit {} - commande {}", productId, orderId);
        } catch (Exception e) {
            // Stock non mis à jour : on annule la commande et on remonte l'erreur
            log.error("Échec de la mise à jour du stock pour commande {}: {}", orderId, e.getMessage());
            orderRepository.findById(orderId).ifPresent(o -> {
                o.setStatus("CANCELLED");
                orderRepository.save(o);
            });
            throw new ProductServiceException(
                    "Erreur lors de la mise à jour du stock. Commande annulée.", e);
        }
    }
}
