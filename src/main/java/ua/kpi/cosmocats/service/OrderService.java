package ua.kpi.cosmocats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.cosmocats.entity.Order;
import ua.kpi.cosmocats.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // @Transactional гарантирует, что если что-то упадет внутри метода,
    // запись в базу не попадет (Rollback).
    @Transactional
    public Order createOrder(String customerEmail) {
        Order order = new Order();
        order.setCustomerEmail(customerEmail);
        order.setCreatedAt(LocalDateTime.now());

        // Генерируем уникальный бизнес-ключ (Natural ID)
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8));

        return orderRepository.save(order);
    }

    // Поиск по Natural ID
    public Order findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}