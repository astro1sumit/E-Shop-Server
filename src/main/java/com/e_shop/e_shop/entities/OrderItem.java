package com.e_shop.e_shop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;    // Matches 'productId' from React
    private String title;      // Matches 'title' from React
    private Integer quantity;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // The foreign key column in PGSQL
    private Order order;
}