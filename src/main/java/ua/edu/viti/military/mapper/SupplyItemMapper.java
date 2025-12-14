package ua.edu.viti.military.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.edu.viti.military.dto.request.SupplyItemRequest;
import ua.edu.viti.military.dto.response.SupplyItemResponse;
import ua.edu.viti.military.entity.SupplyItem;

@Mapper(componentModel = "spring", uses = {SupplyCategoryMapper.class, WarehouseMapper.class})
public interface SupplyItemMapper {

    SupplyItemResponse toResponse(SupplyItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    SupplyItem toEntity(SupplyItemRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "warehouse", ignore = true)
    void updateEntityFromRequest(SupplyItemRequest request, @MappingTarget SupplyItem item);
}
