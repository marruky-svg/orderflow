package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySupplierId(Long supplierId);
    List<Product> findByNameContainingIgnoreCase(String name);
}
