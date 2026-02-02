package com.e_shop.e_shop.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The unique ID from Clerk (e.g., user_2b...)
    private String userId;

    private Long productId;
    private String title;
    private String thumbnail;
    private Double price;
}