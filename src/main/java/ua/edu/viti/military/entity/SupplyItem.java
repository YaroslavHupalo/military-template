package ua.edu.viti.military.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "supply_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "batch_number", nullable = false)
    private String batchNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "hazard_class", nullable = false)
    private HazardClass hazardClass;

    @Column(name = "storage_conditions")
    private String storageConditions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private SupplyCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;
}
