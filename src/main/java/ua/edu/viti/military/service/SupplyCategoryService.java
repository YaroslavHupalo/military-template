package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.SupplyCategoryRequest;
import ua.edu.viti.military.dto.response.SupplyCategoryResponse;
import ua.edu.viti.military.entity.SupplyCategory;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.mapper.SupplyCategoryMapper;
import ua.edu.viti.military.repository.SupplyCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyCategoryService {

    private final SupplyCategoryRepository repository;
    private final SupplyCategoryMapper mapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "categories_list")
    public List<SupplyCategoryResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "categories", key = "#id")
    public SupplyCategoryResponse getById(Long id) {
        SupplyCategory category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapper.toResponse(category);
    }

    @Transactional
    @CacheEvict(value = "categories_list", allEntries = true)
    public SupplyCategoryResponse create(SupplyCategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Category with name " + request.getName() + " already exists");
        }
        SupplyCategory category = mapper.toEntity(request);
        return mapper.toResponse(repository.save(category));
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "categories", key = "#id") },
            evict = { @CacheEvict(value = "categories_list", allEntries = true) }
    )
    public SupplyCategoryResponse update(Long id, SupplyCategoryRequest request) {
        SupplyCategory category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        mapper.updateEntityFromRequest(request, category);
        
        return mapper.toResponse(repository.save(category));
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "categories", key = "#id"),
                    @CacheEvict(value = "categories_list", allEntries = true)
            }
    )
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
