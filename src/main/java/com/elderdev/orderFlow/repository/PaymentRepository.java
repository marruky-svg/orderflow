package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Order;
import com.elderdev.orderFlow.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
    Optional<Payment> findByStripePaymentId(String stripePaymentId);
}

