package com.e_shop.e_shop.controllers;

import com.e_shop.e_shop.entities.Order;
import com.e_shop.e_shop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order saveOrder(Order order) {
        // Link children to parent so order_id is not null in DB
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
        order.setDeliveryStatus("PENDING");
        order.setPaymentStatus("CREATED");
        return orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}