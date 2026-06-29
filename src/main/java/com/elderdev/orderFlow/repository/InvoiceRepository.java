package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Invoice;
import com.elderdev.orderFlow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByOrderId(Long orderId);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    Long countByCreatedAtBetween(LocalDateTime createdAtAfter, LocalDateTime createdAtBefore);

}
