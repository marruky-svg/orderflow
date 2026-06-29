package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Category;
import com.elderdev.orderFlow.entity.Product;
import com.elderdev.orderFlow.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
    List<Product> findBySupplier(Supplier supplier);
    List<Product> findByNameContainingIgnoreCase(String name);
}
