package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.WarehouseRequest;
import ua.edu.viti.military.dto.response.WarehouseResponse;
import ua.edu.viti.military.entity.Warehouse;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.mapper.WarehouseMapper;
import ua.edu.viti.military.repository.WarehouseRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository repository;
    private final WarehouseMapper mapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "warehouses_list")
    public List<WarehouseResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "warehouses", key = "#id")
    public WarehouseResponse getById(Long id) {
        Warehouse warehouse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
        return mapper.toResponse(warehouse);
    }

    @Transactional
    @CacheEvict(value = "warehouses_list", allEntries = true)
    public WarehouseResponse create(WarehouseRequest request) {
        Warehouse warehouse = mapper.toEntity(request);
        return mapper.toResponse(repository.save(warehouse));
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "warehouses", key = "#id") },
            evict = { @CacheEvict(value = "warehouses_list", allEntries = true) }
    )
    public WarehouseResponse update(Long id, WarehouseRequest request) {
        Warehouse warehouse = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));

        mapper.updateEntityFromRequest(request, warehouse);

        return mapper.toResponse(repository.save(warehouse));
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "warehouses", key = "#id"),
                    @CacheEvict(value = "warehouses_list", allEntries = true)
            }
    )
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Warehouse not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
