package ua.edu.viti.military.dto.response;

import lombok.Data;
import ua.edu.viti.military.entity.HazardClass;

import java.time.LocalDate;

@Data
public class SupplyItemResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private LocalDate expirationDate;
    private String batchNumber;
    private HazardClass hazardClass;
    private String storageConditions;
    private SupplyCategoryResponse category;
    private WarehouseResponse warehouse;
}
