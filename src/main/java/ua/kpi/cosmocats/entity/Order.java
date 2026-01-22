package ua.kpi.cosmocats.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "orders_id_seq", allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String customerEmail;
}