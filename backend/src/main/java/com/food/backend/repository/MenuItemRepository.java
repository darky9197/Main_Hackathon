package com.food.backend.repository;
import com.food.backend.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.UUID;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    List<MenuItem> findByRestaurantId(UUID restaurantId);
    List<MenuItem> findByNameContainingIgnoreCaseAndRestaurantId(String name, UUID restaurantId);
}
