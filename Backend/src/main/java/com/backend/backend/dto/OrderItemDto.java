<<<<<<< HEAD
package com.food.backend.dto;

import lombok.Data;
import java.util.UUID;

=======
package com.backend.backend.dto;
import lombok.Data;
import java.util.UUID;
>>>>>>> origin/dev-4
@Data
public class OrderItemDto {
    private UUID id;
    private UUID menuItemId;
    private Integer quantity;
}
