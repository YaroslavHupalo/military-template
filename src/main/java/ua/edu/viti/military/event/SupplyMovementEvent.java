package ua.edu.viti.military.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.edu.viti.military.entity.MovementType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SupplyMovementEvent {
    private Long movementId;
    private Long itemId;
    private int amount;
    private MovementType type;
    private LocalDateTime timestamp;
}
