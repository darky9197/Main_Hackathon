package com.food.backend.controller;

import com.food.backend.model.MenuItem;
import com.food.backend.model.Restaurant;
import com.food.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAllRestaurants(@RequestParam(required = false) String search) {
        return restaurantService.getAllRestaurants(search);
    }

    @GetMapping("/{id}/menu")
    public List<MenuItem> getMenu(@PathVariable UUID id, @RequestParam(required = false) String search) {
        return restaurantService.getMenu(id, search);
    }

    @PostMapping
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getAttribute("role"))) return ResponseEntity.status(403).build();
        return ResponseEntity.ok(restaurantService.addRestaurant(restaurant));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRestaurant(@PathVariable UUID id, @RequestBody Restaurant restaurantDetails, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getAttribute("role"))) return ResponseEntity.status(403).build();
        Restaurant updated = restaurantService.updateRestaurant(id, restaurantDetails);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable UUID id, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getAttribute("role"))) return ResponseEntity.status(403).build();
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/menu")
    public ResponseEntity<?> addMenuItem(@PathVariable UUID id, @RequestBody MenuItem item, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getAttribute("role"))) return ResponseEntity.status(403).build();
        MenuItem saved = restaurantService.addMenuItem(id, item);
        if (saved == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{restId}/menu/{menuId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable UUID restId, @PathVariable UUID menuId, HttpServletRequest request) {
        if (!"ADMIN".equals(request.getAttribute("role"))) return ResponseEntity.status(403).build();
        restaurantService.deleteMenuItem(menuId);
        return ResponseEntity.ok().build();
    }
}
