package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        String email,
        Role role,
        LocalDateTime createdAt
){}
