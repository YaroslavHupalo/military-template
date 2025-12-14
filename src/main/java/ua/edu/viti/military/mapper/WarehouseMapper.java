package ua.edu.viti.military.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ua.edu.viti.military.dto.request.WarehouseRequest;
import ua.edu.viti.military.dto.response.WarehouseResponse;
import ua.edu.viti.military.entity.Warehouse;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseResponse toResponse(Warehouse warehouse);
    Warehouse toEntity(WarehouseRequest request);
    void updateEntityFromRequest(WarehouseRequest request, @MappingTarget Warehouse warehouse);
}
