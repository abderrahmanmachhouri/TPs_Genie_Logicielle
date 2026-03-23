package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.dto.OrderRequest;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // GET /api/orders - Lister toutes les commandes
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // GET /api/orders/{id} - Récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // POST /api/orders?productId=1&quantity=2 - Créer une commande (style TP)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long productId,
                                              @RequestParam Integer quantity) {
        Order order = orderService.createOrder(productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // POST /api/orders/create - Créer via JSON body (bonus)
    @PostMapping("/create")
    public ResponseEntity<Order> createOrderFromBody(@Valid @RequestBody OrderRequest request) {
        Order order = orderService.createOrder(request.getProductId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // PATCH /api/orders/{id}/status - Mettre à jour le statut
    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id,
                                               @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // DELETE /api/orders/{id} - Annuler une commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/orders/status/{status} - Filtrer par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
}
