package com.elderdev.orderFlow.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Integer quantity,
        String productName,
        BigDecimal unitPrice
){}
