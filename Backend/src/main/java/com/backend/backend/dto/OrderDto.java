<<<<<<< HEAD
package com.food.backend.dto;

=======
package com.backend.backend.dto;
>>>>>>> origin/dev-4
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
<<<<<<< HEAD

=======
>>>>>>> origin/dev-4
@Data
public class OrderDto {
    private UUID id;
    private UUID userId;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}
