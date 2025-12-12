package ua.edu.viti.military.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.viti.military.entity.SupplyItem;

import java.util.List;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {
    List<SupplyItem> findByCategoryId(Long categoryId);
    List<SupplyItem> findByWarehouseId(Long warehouseId);
}
