package com.ecommerce.monolith.order.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemDTO> items;
}