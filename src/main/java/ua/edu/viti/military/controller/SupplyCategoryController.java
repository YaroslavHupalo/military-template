package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.SupplyCategoryRequest;
import ua.edu.viti.military.dto.response.SupplyCategoryResponse;
import ua.edu.viti.military.service.SupplyCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Supply Categories", description = "API for managing supply categories")
public class SupplyCategoryController {

    private final SupplyCategoryService service;

    @GetMapping
    @Operation(summary = "Get all categories")
    public List<SupplyCategoryResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public SupplyCategoryResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new category")
    public SupplyCategoryResponse create(@Valid @RequestBody SupplyCategoryRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category")
    public SupplyCategoryResponse update(@PathVariable Long id, @Valid @RequestBody SupplyCategoryRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete category")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
