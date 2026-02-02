package com.e_shop.e_shop.controllers;

import com.e_shop.e_shop.entities.Order;
import com.e_shop.e_shop.repository.OrderRepository;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Order orderData) {
        try {
            // 1. Save order to DB via Service
            Order savedOrder = orderService.saveOrder(orderData);

            // 2. Initialize Razorpay Client using @Value variables
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            // 3. Prepare Razorpay Order Request
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int)(savedOrder.getTotalAmount() * 100));
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + savedOrder.getId());

            // 4. Create order in Razorpay
            com.razorpay.Order rzpOrder = razorpay.orders.create(orderRequest);
            String rzpOrderId = rzpOrder.get("id");

            // 5. Update DB with the Razorpay ID
            savedOrder.setRazorpayOrderId(rzpOrderId);
            orderService.updateOrder(savedOrder);

            // 6. Return response to Frontend
            JSONObject responseJson = new JSONObject();
            responseJson.put("razorpayOrderId", rzpOrderId);
            responseJson.put("amount", (int)(savedOrder.getTotalAmount() * 100));
            responseJson.put("key", keyId); // Dynamic key from properties
            responseJson.put("orderId", savedOrder.getId());

            return ResponseEntity.ok(responseJson.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, String> request) {
        String rzpOrderId = request.get("razorpayOrderId");
        String status = request.get("status");

        Order order = orderRepository.findByRazorpayOrderId(rzpOrderId);
        if (order != null) {
            order.setPaymentStatus(status);
            orderRepository.save(order);
            return ResponseEntity.ok("Order status updated");
        }
        return ResponseEntity.status(404).body("Order not found");
    }
}