package com.food.backend.dto;
import lombok.Data;
import java.util.UUID;
@Data
public class OrderItemDto {
    private UUID id;
    private UUID menuItemId;
    private Integer quantity;
}
