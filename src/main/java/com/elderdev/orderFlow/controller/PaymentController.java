package com.elderdev.orderFlow.controller;

import com.elderdev.orderFlow.dto.PaymentResponse;
import com.elderdev.orderFlow.entity.Payment;
import com.elderdev.orderFlow.service.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(paymentService.findById(id)));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> selectByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(toResponse(paymentService.findByOrderId(orderId)));
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<String> create(@PathVariable Long orderId) throws StripeException {
        return ResponseEntity.status(201).body(paymentService.createPayment(orderId));
    }

    @PutMapping("/confirm/{stripePaymentId}")
    public ResponseEntity<Void> confirm(@PathVariable String stripePaymentId) throws StripeException {
        paymentService.confirmPayment(stripePaymentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<Void> refund(@PathVariable Long id) throws StripeException {
        paymentService.refundPayment(id);
        return ResponseEntity.noContent().build();
    }


    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getAmount(),
                payment.getStripePaymentId(),
                payment.getCreatedAt(),
                payment.getOrder().getId()
        );
    }
}
