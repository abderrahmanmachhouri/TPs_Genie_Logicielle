package com.ecommerce.monolith.order.mapper;


import com.ecommerce.monolith.order.model.order;
import com.ecommerce.monolith.order.model.OrderItem;
import com.ecommerce.monolith.order.dto.CreateOrderRequest;
import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(order order);
    List<OrderDTO> toDTOList(List<order> orders);
    OrderItemDTO toItemDTO(OrderItem item);
    List<OrderItemDTO> toItemDTOList(List<OrderItem> items);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true) // items gérés manuellement
    order toEntity(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "items", ignore = true)
    void updateEntity(CreateOrderRequest request, @MappingTarget order order);
}