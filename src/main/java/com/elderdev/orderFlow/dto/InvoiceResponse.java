package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record InvoiceResponse(
        Long id,
        String invoiceNumber,
        BigDecimal totalAmount,
        InvoiceStatus status,
        LocalDateTime issuedAt,
        Long orderId
){}
