package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.SupplyItemRequest;
import ua.edu.viti.military.dto.response.SupplyItemResponse;
import ua.edu.viti.military.service.SupplyItemService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Tag(name = "Supply Items", description = "API for managing supply items")
public class SupplyItemController {

    private final SupplyItemService service;

    @GetMapping
    @Operation(summary = "Get all supply items")
    public List<SupplyItemResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supply item by ID")
    public SupplyItemResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new supply item")
    public SupplyItemResponse create(@Valid @RequestBody SupplyItemRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update supply item")
    public SupplyItemResponse update(@PathVariable Long id, @Valid @RequestBody SupplyItemRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete supply item")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
