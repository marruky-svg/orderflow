package com.elderdev.orderFlow.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true ,nullable = false, length = 100)
    private String name;
    @Column(length = 255)
    private String description;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

}
