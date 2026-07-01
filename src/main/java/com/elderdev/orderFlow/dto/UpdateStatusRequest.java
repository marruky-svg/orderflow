package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull OrderStatus status) {
}
