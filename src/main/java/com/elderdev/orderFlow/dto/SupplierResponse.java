package com.elderdev.orderFlow.dto;

import java.time.LocalDateTime;

public record SupplierResponse(
        String name,
        String email,
        String phone,
        String address,
        LocalDateTime createdAt
)
{}
