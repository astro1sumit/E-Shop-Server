package com.e_shop.e_shop.repository;

import com.e_shop.e_shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Fetches past orders filtered by the Clerk User ID
    List<Order> findByUserId(String userId);

    // Used by your payment status update logic
    Order findByRazorpayOrderId(String razorpayOrderId);
}