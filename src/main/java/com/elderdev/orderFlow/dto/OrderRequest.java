package com.elderdev.orderFlow.dto;

import com.elderdev.orderFlow.entity.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest(
        @NotNull Long customerId,
        @NotBlank @Size(max = 255) String shippingAddress,
        @NotNull @NotEmpty List<OrderItemRequest> items
        ){}
