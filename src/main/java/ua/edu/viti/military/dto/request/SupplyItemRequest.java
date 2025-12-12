package ua.edu.viti.military.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ua.edu.viti.military.entity.HazardClass;

import java.time.LocalDate;

@Data
public class SupplyItemRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    private LocalDate expirationDate;

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    @NotNull(message = "Hazard class is required")
    private HazardClass hazardClass;

    private String storageConditions;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Warehouse ID is required")
    private Long warehouseId;
}
