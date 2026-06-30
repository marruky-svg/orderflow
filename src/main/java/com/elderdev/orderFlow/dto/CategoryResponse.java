package com.elderdev.orderFlow.dto;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt
) {}
