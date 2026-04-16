package com.food.backend.repository;
import com.food.backend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
}
