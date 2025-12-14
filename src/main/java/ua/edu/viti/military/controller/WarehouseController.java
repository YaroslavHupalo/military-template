package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.WarehouseRequest;
import ua.edu.viti.military.dto.response.WarehouseResponse;
import ua.edu.viti.military.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
@RequiredArgsConstructor
@Tag(name = "Warehouses", description = "API for managing warehouses")
public class WarehouseController {

    private final WarehouseService service;

    @GetMapping
    @Operation(summary = "Get all warehouses")
    public List<WarehouseResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get warehouse by ID")
    public WarehouseResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new warehouse")
    public WarehouseResponse create(@Valid @RequestBody WarehouseRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update warehouse")
    public WarehouseResponse update(@PathVariable Long id, @Valid @RequestBody WarehouseRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete warehouse")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
