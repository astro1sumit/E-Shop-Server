package com.e_shop.e_shop.entities;

import com.e_shop.e_shop.entities.OrderItem;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders", schema = "public")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String fullName;
    private String email;
    private String address;
    private String city;
    private String zipCode;
    private Double totalAmount;
    private String paymentStatus;
    private String deliveryStatus;
    private String razorpayOrderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>(); // Initialize to prevent NullPointerException

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
}