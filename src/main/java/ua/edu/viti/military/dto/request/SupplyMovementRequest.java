package ua.edu.viti.military.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.edu.viti.military.entity.MovementType;

@Data
public class SupplyMovementRequest {
    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotNull(message = "Movement type is required")
    private MovementType type;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private String reason;
}
