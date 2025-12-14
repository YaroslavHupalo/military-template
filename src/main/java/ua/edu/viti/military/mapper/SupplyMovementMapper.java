package ua.edu.viti.military.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.edu.viti.military.dto.request.SupplyMovementRequest;
import ua.edu.viti.military.dto.response.SupplyMovementResponse;
import ua.edu.viti.military.entity.SupplyMovement;

@Mapper(componentModel = "spring")
public interface SupplyMovementMapper {

    @Mapping(target = "itemName", source = "item.name")
    SupplyMovementResponse toResponse(SupplyMovement movement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "item", ignore = true)
    @Mapping(target = "version", ignore = true)
    SupplyMovement toEntity(SupplyMovementRequest request);
}
