<<<<<<< HEAD
package com.food.backend.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

=======
package com.backend.backend.dto;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;
>>>>>>> origin/dev-4
@Data
public class MenuItemDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private UUID restaurantId;
}
