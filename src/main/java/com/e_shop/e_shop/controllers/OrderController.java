package com.e_shop.e_shop.controllers;


import com.e_shop.e_shop.entities.Order;
import com.e_shop.e_shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000") // Allow React to access this
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        order.setPaymentStatus("PENDING"); // Default status
        // Here you would integrate Razorpay/Stripe logic later
        return orderRepository.save(order);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Order>> getOrderHistory(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject(); // Extract user ID from the Clerk token
        List<Order> orders = orderRepository.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}