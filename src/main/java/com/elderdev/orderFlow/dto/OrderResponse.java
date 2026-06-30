package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        OrderStatus status,
        String customerName,
        List<OrderItemResponse> items,
        String shippingAddress,
        BigDecimal totalAmount,
        LocalDateTime createdAt
){}
