package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.PaymentMethod;
import com.elderdev.orderFlow.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        PaymentMethod method,
        PaymentStatus status,
        BigDecimal amount,
        String stripePaymentId,
        LocalDateTime createdAt,
        Long orderId
){}
