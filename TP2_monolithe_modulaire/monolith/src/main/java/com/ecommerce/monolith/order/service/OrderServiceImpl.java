package com.ecommerce.monolith.order.service;

import com.ecommerce.monolith.customer.service.CustomerService;
import com.ecommerce.monolith.order.model.order;
import com.ecommerce.monolith.order.model.OrderItem;
import com.ecommerce.monolith.order.repository.OrderRepository;
import com.ecommerce.monolith.order.dto.CreateOrderRequest;
import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.mapper.OrderMapper;
import com.ecommerce.monolith.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderMapper.toDTOList(orderRepository.findAll());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional
    public OrderDTO createOrder(CreateOrderRequest request) {
        // Vérifier que le client existe
        if (!customerService.existsById(request.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found with id: " + request.getCustomerId());
        }

        order order = new order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            // Vérifier que le produit existe et récupérer son prix actuel
            var productDTO = productService.getProductById(itemRequest.getProductId());
            if (productDTO == null) {
                throw new EntityNotFoundException("Product not found with id: " + itemRequest.getProductId());
            }

            OrderItem item = new OrderItem();
            item.setProductId(itemRequest.getProductId());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(productDTO.getPrice());
            order.getItems().add(item);
        }

        order saved = orderRepository.save(order);
        return orderMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(Long id, CreateOrderRequest request) {
        order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        if (!customerService.existsById(request.getCustomerId())) {
            throw new EntityNotFoundException("Customer not found with id: " + request.getCustomerId());
        }

        order.setCustomerId(request.getCustomerId());
        order.getItems().clear();

        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            var productDTO = productService.getProductById(itemRequest.getProductId());
            if (productDTO == null) {
                throw new EntityNotFoundException("Product not found with id: " + itemRequest.getProductId());
            }
            OrderItem item = new OrderItem();
            item.setProductId(itemRequest.getProductId());
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(productDTO.getPrice());
            order.getItems().add(item);
        }

        order updated = orderRepository.save(order);
        return orderMapper.toDTO(updated);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        if (!customerService.existsById(customerId)) {
            throw new EntityNotFoundException("Customer not found with id: " + customerId);
        }
        List<order> orders = orderRepository.findByCustomerId(customerId);
        return orderMapper.toDTOList(orders);
    }
}