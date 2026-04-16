package com.food.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private UUID userId;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}
