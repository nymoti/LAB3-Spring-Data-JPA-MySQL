package com.example.lab3.service;

import com.example.lab3.dto.OrderDto;
import com.example.lab3.dto.OrderItemDto;
import com.example.lab3.entity.Order;
import com.example.lab3.entity.OrderItem;
import com.example.lab3.entity.Product;
import com.example.lab3.repository.OrderRepository;
import com.example.lab3.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // La commande et ses articles sont enregistrés dans la même transaction
    @Transactional
    public OrderDto create(OrderDto dto) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        for (OrderItemDto itemDto : dto.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'id : " + itemDto.productId()));
            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDto.quantity());
            order.addItem(item);
        }
        return toDto(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable avec l'id : " + id));
        return toDto(order);
    }

    private OrderDto toDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderDate(),
                order.getItems().stream()
                        .map(item -> new OrderItemDto(
                                item.getId(),
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity()))
                        .toList());
    }
}
