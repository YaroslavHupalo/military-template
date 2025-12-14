package ua.edu.viti.military.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.viti.military.entity.SupplyMovement;

import java.util.List;

@Repository
public interface SupplyMovementRepository extends JpaRepository<SupplyMovement, Long> {
    List<SupplyMovement> findByItemId(Long itemId);
}
