package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.SupplyCategoryRequest;
import ua.edu.viti.military.dto.response.SupplyCategoryResponse;
import ua.edu.viti.military.entity.SupplyCategory;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.repository.SupplyCategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyCategoryService {

    private final SupplyCategoryRepository repository;

    @Transactional(readOnly = true)
    public List<SupplyCategoryResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SupplyCategoryResponse getById(Long id) {
        SupplyCategory category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToResponse(category);
    }

    @Transactional
    public SupplyCategoryResponse create(SupplyCategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Category with name " + request.getName() + " already exists");
        }
        SupplyCategory category = SupplyCategory.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return mapToResponse(repository.save(category));
    }

    @Transactional
    public SupplyCategoryResponse update(Long id, SupplyCategoryRequest request) {
        SupplyCategory category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        
        return mapToResponse(repository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private SupplyCategoryResponse mapToResponse(SupplyCategory category) {
        SupplyCategoryResponse response = new SupplyCategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        return response;
    }
}
