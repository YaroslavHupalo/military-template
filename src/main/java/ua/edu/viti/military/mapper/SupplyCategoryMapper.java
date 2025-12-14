package ua.edu.viti.military.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.edu.viti.military.dto.request.SupplyCategoryRequest;
import ua.edu.viti.military.dto.response.SupplyCategoryResponse;
import ua.edu.viti.military.entity.SupplyCategory;

@Mapper(componentModel = "spring")
public interface SupplyCategoryMapper {
    SupplyCategoryResponse toResponse(SupplyCategory category);
    SupplyCategory toEntity(SupplyCategoryRequest request);
    void updateEntityFromRequest(SupplyCategoryRequest request, @MappingTarget SupplyCategory category);
}
