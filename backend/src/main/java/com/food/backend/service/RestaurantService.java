package com.food.backend.service;

import com.food.backend.model.MenuItem;
import com.food.backend.model.Restaurant;
import com.food.backend.repository.MenuItemRepository;
import com.food.backend.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepo;
    @Autowired
    private MenuItemRepository menuItemRepo;

    public List<Restaurant> getAllRestaurants(String search) {
        if (search != null && !search.trim().isEmpty()) {
            return restaurantRepo.findByNameContainingIgnoreCase(search);
        }
        return restaurantRepo.findAll();
    }

    public List<MenuItem> getMenu(UUID restaurantId, String search) {
        if (search != null && !search.trim().isEmpty()) {
            return menuItemRepo.findByNameContainingIgnoreCaseAndRestaurantId(search, restaurantId);
        }
        return menuItemRepo.findByRestaurantId(restaurantId);
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public Restaurant updateRestaurant(UUID id, Restaurant restaurantDetails) {
        Restaurant r = restaurantRepo.findById(id).orElse(null);
        if (r == null) return null;
        r.setName(restaurantDetails.getName());
        return restaurantRepo.save(r);
    }

    public void deleteRestaurant(UUID id) {
        restaurantRepo.deleteById(id);
    }

    public MenuItem addMenuItem(UUID restaurantId, MenuItem item) {
        Restaurant r = restaurantRepo.findById(restaurantId).orElse(null);
        if (r == null) return null;
        item.setRestaurant(r);
        return menuItemRepo.save(item);
    }

    public void deleteMenuItem(UUID menuId) {
        menuItemRepo.deleteById(menuId);
    }
}
