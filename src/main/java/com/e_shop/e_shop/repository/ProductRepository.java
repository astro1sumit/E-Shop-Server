package com.e_shop.e_shop.repository;

import com.e_shop.e_shop.entities.Product; // Ensure this matches your Entity name
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // This magic method finds products where title contains the keyword (case-insensitive)
    List<Product> findByTitleContainingIgnoreCase(String keyword);
}
