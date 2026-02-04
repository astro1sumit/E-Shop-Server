package com.e_shop.e_shop.entities;

import com.e_shop.e_shop.entities.Order;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_item", schema = "public")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String title;
    private Integer quantity;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // References the 'order_id' column in the 'orders' table
    public Order order;
}