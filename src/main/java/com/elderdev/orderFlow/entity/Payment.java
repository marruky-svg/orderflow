package com.elderdev.orderFlow.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="order_id", nullable = false)
    private Order order;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;
    private String stripePaymentId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
