package com.food.backend.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;
@Data
public class MenuItemDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private UUID restaurantId;
}
