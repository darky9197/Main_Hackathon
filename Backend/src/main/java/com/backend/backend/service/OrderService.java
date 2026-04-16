package com.food.backend.service;

import com.food.backend.model.Order;
import com.food.backend.model.User;
import com.food.backend.repository.OrderRepository;
import com.food.backend.repository.UserRepository;
import com.food.backend.config.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private EmailService emailService;

    public Order placeOrder(UUID userId, Order order) throws Exception {
        User user = userRepo.findById(userId).orElse(null);
        if (user == null) {
            throw new Exception("User not found");
        }
        order.setUser(user);
        Order savedOrder = orderRepo.save(order);
        emailService.sendOrderConfirmation(user.getEmail(), savedOrder.getId(), savedOrder.getTotalAmount());
        return savedOrder;
    }

    public List<Order> getUserOrders(UUID userId) {
        return orderRepo.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order updateOrderStatus(UUID orderId, String newStatus) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return null;
        
        order.setStatus(newStatus);
        orderRepo.save(order);
        
        if (order.getUser() != null) {
            emailService.sendStatusUpdate(order.getUser().getEmail(), order.getId(), newStatus);
        }
        return order;
    }

    public Order cancelOrder(UUID orderId, UUID currentUserId) throws IllegalAccessException, IllegalStateException {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return null;

        if (order.getUser() == null || !order.getUser().getId().equals(currentUserId)) {
            throw new IllegalAccessException("Access denied");
        }

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalStateException("Can only cancel PENDING orders");
        }

        order.setStatus("CANCELLED");
        orderRepo.save(order);

        if (order.getUser() != null) {
            emailService.sendStatusUpdate(order.getUser().getEmail(), order.getId(), "CANCELLED");
        }

        return order;
    }
}
