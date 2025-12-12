package ua.edu.viti.military.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.viti.military.dto.request.SupplyMovementRequest;
import ua.edu.viti.military.dto.response.SupplyMovementResponse;
import ua.edu.viti.military.entity.MovementType;
import ua.edu.viti.military.entity.SupplyItem;
import ua.edu.viti.military.entity.SupplyMovement;
import ua.edu.viti.military.exception.ResourceNotFoundException;
import ua.edu.viti.military.repository.SupplyItemRepository;
import ua.edu.viti.military.repository.SupplyMovementRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplyMovementService {

    private final SupplyMovementRepository repository;
    private final SupplyItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<SupplyMovementResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SupplyMovementResponse create(SupplyMovementRequest request) {
        SupplyItem item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Supply item not found with id: " + request.getItemId()));

        validateMovement(item, request);
        updateItemQuantity(item, request);

        SupplyMovement movement = SupplyMovement.builder()
                .type(request.getType())
                .item(item)
                .quantity(request.getQuantity())
                .date(LocalDateTime.now())
                .reason(request.getReason())
                .build();

        return mapToResponse(repository.save(movement));
    }

    private void validateMovement(SupplyItem item, SupplyMovementRequest request) {
        if (request.getType() == MovementType.ISSUE) {
            if (item.getQuantity() < request.getQuantity()) {
                throw new IllegalArgumentException("Insufficient quantity. Available: " + item.getQuantity());
            }
            if (item.getExpirationDate() != null && item.getExpirationDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Cannot issue expired item. Expiration date: " + item.getExpirationDate());
            }
        }
        if (request.getType() == MovementType.WRITE_OFF) {
             if (item.getQuantity() < request.getQuantity()) {
                throw new IllegalArgumentException("Cannot write off more than available. Available: " + item.getQuantity());
            }
        }
    }

    private void updateItemQuantity(SupplyItem item, SupplyMovementRequest request) {
        switch (request.getType()) {
            case ISSUE:
            case WRITE_OFF:
                item.setQuantity(item.getQuantity() - request.getQuantity());
                break;
            case RECEIPT:
            case RETURN:
                item.setQuantity(item.getQuantity() + request.getQuantity());
                break;
        }
        itemRepository.save(item);
    }

    private SupplyMovementResponse mapToResponse(SupplyMovement movement) {
        SupplyMovementResponse response = new SupplyMovementResponse();
        response.setId(movement.getId());
        response.setType(movement.getType());
        response.setItemName(movement.getItem().getName());
        response.setQuantity(movement.getQuantity());
        response.setDate(movement.getDate());
        response.setReason(movement.getReason());
        return response;
    }
}
