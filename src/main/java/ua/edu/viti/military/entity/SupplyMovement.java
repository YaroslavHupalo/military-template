package ua.edu.viti.military.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "supply_movements")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private SupplyItem item;

    @Column(nullable = false)
    private Integer quantity;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    private String reason;

    @Column(length = 100)
    private String recipientName;

    @Column(length = 50)
    private String recipientUnit;

    @Column(length = 100)
    private String performedBy;

    @Version
    private Long version;
}
