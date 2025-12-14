package ua.edu.viti.military.dto.response;

import lombok.Data;
import ua.edu.viti.military.entity.MovementType;

import java.time.LocalDateTime;

@Data
public class SupplyMovementResponse {
    private Long id;
    private MovementType type;
    private String itemName;
    private Integer quantity;
    private LocalDateTime date;
    private String reason;
    private String recipientName;
    private String recipientUnit;
    private String performedBy;
}
