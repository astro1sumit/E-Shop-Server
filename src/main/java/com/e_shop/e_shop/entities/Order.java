package com.e_shop.e_shop.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId; // Link to the authenticated Clerk user
    private String fullName;
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private Double totalAmount;
    private String paymentStatus;
    private String deliveryStatus; // Initialized as "PENDING"
    private String razorpayOrderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}