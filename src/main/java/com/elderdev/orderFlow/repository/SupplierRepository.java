package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);
    boolean existsByEmail(String email);
}
