package com.e_shop.e_shop.controllers;

import com.e_shop.e_shop.entities.Wishlist;
import com.e_shop.e_shop.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    // GET: Fetch all items in the authenticated user's wishlist
    @GetMapping
    public List<Wishlist> getWishlist(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject(); // Extract User ID from Clerk JWT
        return wishlistRepository.findByUserId(userId);
    }

    // POST: Add or Remove an item based on its existence in the DB
    @PostMapping("/toggle")
    public ResponseEntity<?> toggleWishlist(@RequestBody Wishlist item, @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        Long productId = item.getProductId();

        // PRINT THESE TO YOUR CONSOLE
        System.out.println("--- Wishlist Toggle Attempt ---");
        System.out.println("Logged in User (from Clerk): " + userId);
        System.out.println("Product ID: " + productId);

        var existing = wishlistRepository.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent()) {
            System.out.println("MATCH FOUND: Deleting item...");
            wishlistRepository.delete(existing.get());
            return ResponseEntity.ok(Map.of("status", "Removed", "productId", productId));
        } else {
            System.out.println("NO MATCH: Adding item for user " + userId);
            item.setUserId(userId);
            Wishlist saved = wishlistRepository.save(item);
            return ResponseEntity.ok(Map.of("status", "Added", "product", saved));
        }
    }
}