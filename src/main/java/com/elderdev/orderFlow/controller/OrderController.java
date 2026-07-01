package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.*;
import com.elderdev.orderFlow.entity.Order;
import com.elderdev.orderFlow.entity.OrderItem;
import com.elderdev.orderFlow.service.CustomerService;
import com.elderdev.orderFlow.service.OrderService;
import com.elderdev.orderFlow.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> selectAll() {
        return ResponseEntity.ok(orderService.findAll().stream()
                .map(this::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(orderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
           return ResponseEntity.status(201).body(toResponse(orderService.create(request.customerId(),
                   toOrderItem(request.items()), request.shippingAddress())));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
                                                      @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(toResponse(orderService.updateStatus(id, request.status())));
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> findByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.findByCustomerId(customerId).stream()
                .map(this::toResponse)
                .toList());
    }

    private OrderResponse toResponse(Order order) {
        String customerName = order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName();
        return new OrderResponse(
                order.getId(),
                order.getStatus(),
                customerName,
                toOrderItemResponse(order.getItems()),
                order.getShippingAddress(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

    private List<OrderItemResponse> toOrderItemResponse(List<OrderItem> items) {
        List<OrderItemResponse> response = new ArrayList<>();
        for (var item : items) {
            OrderItemResponse orderItem = new OrderItemResponse(
                    item.getId(),
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getUnitPrice()
            );
            response.add(orderItem);
        }
        return response;
    }

    private List<OrderItem> toOrderItem(List<OrderItemRequest> request) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (var item : request) {
            var product = productService.findById(item.productId());
            var orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(item.quantity())
                    .build();
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
