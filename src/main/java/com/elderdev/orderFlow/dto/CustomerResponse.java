package com.elderdev.orderFlow.dto;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String address,
        LocalDateTime createdAt,
        String username
){}
