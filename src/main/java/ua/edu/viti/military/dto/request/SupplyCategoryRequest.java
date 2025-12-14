package ua.edu.viti.military.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplyCategoryRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
}
