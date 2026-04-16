package com.food.backend.dto;
import lombok.Data;
import java.util.UUID;
@Data
public class RestaurantDto {
    private UUID id;
    private String name;
}
