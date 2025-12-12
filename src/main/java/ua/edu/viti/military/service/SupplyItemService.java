package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.SupplyItemRequest;
import ua.edu.viti.military.dto.response.SupplyCategoryResponse;
import ua.edu.viti.military.dto.response.SupplyItemResponse;
import ua.edu.viti.military.dto.response.WarehouseResponse;
import ua.edu.viti.military.entity.SupplyCategory;
import ua.edu.viti.military.entity.SupplyItem;
import ua.edu.viti.military.entity.Warehouse;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.repository.SupplyCategoryRepository;
import ua.edu.viti.military.repository.SupplyItemRepository;
import ua.edu.viti.military.repository.WarehouseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyItemService {

    private final SupplyItemRepository repository;
    private final SupplyCategoryRepository categoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true)
    public List<SupplyItemResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplyItemResponse getById(Long id) {
        SupplyItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply item not found with id: " + id));
        return mapToResponse(item);
    }

    @Transactional
    public SupplyItemResponse create(SupplyItemRequest request) {
        SupplyCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + request.getWarehouseId()));

        SupplyItem item = SupplyItem.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .expirationDate(request.getExpirationDate())
                .batchNumber(request.getBatchNumber())
                .hazardClass(request.getHazardClass())
                .storageConditions(request.getStorageConditions())
                .category(category)
                .warehouse(warehouse)
                .build();

        return mapToResponse(repository.save(item));
    }

    @Transactional
    public SupplyItemResponse update(Long id, SupplyItemRequest request) {
        SupplyItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply item not found with id: " + id));

        SupplyCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + request.getWarehouseId()));

        item.setName(request.getName());
        item.setQuantity(request.getQuantity());
        item.setExpirationDate(request.getExpirationDate());
        item.setBatchNumber(request.getBatchNumber());
        item.setHazardClass(request.getHazardClass());
        item.setStorageConditions(request.getStorageConditions());
        item.setCategory(category);
        item.setWarehouse(warehouse);

        return mapToResponse(repository.save(item));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Supply item not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private SupplyItemResponse mapToResponse(SupplyItem item) {
        SupplyItemResponse response = new SupplyItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setQuantity(item.getQuantity());
        response.setExpirationDate(item.getExpirationDate());
        response.setBatchNumber(item.getBatchNumber());
        response.setHazardClass(item.getHazardClass());
        response.setStorageConditions(item.getStorageConditions());
        
        SupplyCategoryResponse categoryResponse = new SupplyCategoryResponse();
        categoryResponse.setId(item.getCategory().getId());
        categoryResponse.setName(item.getCategory().getName());
        categoryResponse.setDescription(item.getCategory().getDescription());
        response.setCategory(categoryResponse);

        WarehouseResponse warehouseResponse = new WarehouseResponse();
        warehouseResponse.setId(item.getWarehouse().getId());
        warehouseResponse.setName(item.getWarehouse().getName());
        warehouseResponse.setLocation(item.getWarehouse().getLocation());
        warehouseResponse.setCapacity(item.getWarehouse().getCapacity());
        response.setWarehouse(warehouseResponse);

        return response;
    }
}
