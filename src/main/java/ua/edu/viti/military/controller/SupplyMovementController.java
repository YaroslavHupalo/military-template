package ua.edu.viti.military.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.edu.viti.military.dto.request.SupplyMovementRequest;
import ua.edu.viti.military.dto.response.SupplyMovementResponse;
import ua.edu.viti.military.service.SupplyMovementService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
@Tag(name = "Supply Movements", description = "API for managing supply movements")
public class SupplyMovementController {

    private final SupplyMovementService service;

    @GetMapping
    @Operation(summary = "Get all movements")
    public List<SupplyMovementResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new movement (Issue, Return, Receipt, Write-off)")
    public SupplyMovementResponse create(@Valid @RequestBody SupplyMovementRequest request) {
        return service.create(request);
    }
}
