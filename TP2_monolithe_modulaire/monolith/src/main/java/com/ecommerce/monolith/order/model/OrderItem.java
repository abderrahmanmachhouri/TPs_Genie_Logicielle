package com.ecommerce.monolith.order.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer quantity;

    private BigDecimal price; // prix unitaire au moment de la commande

    @ManyToOne
    @JoinColumn(name = "order_id") // clé étrangère vers Order
    private order order;
}