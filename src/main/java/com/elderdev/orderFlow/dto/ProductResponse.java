package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.Category;
import com.elderdev.orderFlow.entity.Supplier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        LocalDateTime createdAt,
        String categoryName,
        String supplierName
){}
