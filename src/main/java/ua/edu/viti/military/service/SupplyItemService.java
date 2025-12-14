package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.SupplyItemRequest;
import ua.edu.viti.military.dto.response.SupplyItemResponse;
import ua.edu.viti.military.entity.SupplyCategory;
import ua.edu.viti.military.entity.SupplyItem;
import ua.edu.viti.military.entity.Warehouse;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.mapper.SupplyItemMapper;
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
    private final SupplyItemMapper mapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "items_list")
    public List<SupplyItemResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "items", key = "#id")
    public SupplyItemResponse getById(Long id) {
        SupplyItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply item not found with id: " + id));
        return mapper.toResponse(item);
    }

    @Transactional
    @CacheEvict(value = "items_list", allEntries = true)
    public SupplyItemResponse create(SupplyItemRequest request) {
        SupplyCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + request.getWarehouseId()));

        SupplyItem item = mapper.toEntity(request);
        item.setCategory(category);
        item.setWarehouse(warehouse);

        return mapper.toResponse(repository.save(item));
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "items", key = "#id") },
            evict = { @CacheEvict(value = "items_list", allEntries = true) }
    )
    public SupplyItemResponse update(Long id, SupplyItemRequest request) {
        SupplyItem item = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply item not found with id: " + id));

        SupplyCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + request.getCategoryId()));

        Warehouse warehouse = warehouseRepository.findById(request.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + request.getWarehouseId()));

        mapper.updateEntityFromRequest(request, item);
        item.setCategory(category);
        item.setWarehouse(warehouse);

        return mapper.toResponse(repository.save(item));
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "items", key = "#id"),
                    @CacheEvict(value = "items_list", allEntries = true)
            }
    )
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Supply item not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
