package com.food.backend.controller;

import com.food.backend.model.Order;
import com.food.backend.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order, HttpServletRequest request) {
        UUID currentUserId = (UUID) request.getAttribute("userId");
        if (currentUserId == null) return ResponseEntity.status(401).build();
        
        try {
            Order savedOrder = orderService.placeOrder(currentUserId, order);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable UUID userId, HttpServletRequest request) {
        UUID currentUserId = (UUID) request.getAttribute("userId");
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders(HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(role)) {
            return ResponseEntity.status(403).body("Access denied: ADMIN only");
        }
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable UUID id, @RequestBody Map<String, String> payload, HttpServletRequest request) {
        String role = (String) request.getAttribute("role");
        if (!"ADMIN".equals(request.getAttribute("role"))) {
            return ResponseEntity.status(403).body("Access denied: ADMIN only");
        }
        
        String newStatus = payload.get("status");
        Order updated = orderService.updateOrderStatus(id, newStatus);
        
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id, HttpServletRequest request) {
        UUID currentUserId = (UUID) request.getAttribute("userId");
        if (currentUserId == null) return ResponseEntity.status(401).build();

        try {
            Order order = orderService.cancelOrder(id, currentUserId);
            if (order == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(order);
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
