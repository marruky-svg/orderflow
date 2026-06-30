package com.elderdev.orderFlow.dto;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 155) String name,
        @Size(max = 500) String description,
        @NotNull @Positive BigDecimal price,
        @NotNull @PositiveOrZero Integer stockQuantity,
        @NotNull Long categoryId,
        @NotNull Long supplierId
        ) {
}
