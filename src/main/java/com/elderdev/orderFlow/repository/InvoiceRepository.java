package com.elderdev.orderFlow.repository;

import com.elderdev.orderFlow.entity.Invoice;
import com.elderdev.orderFlow.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByOrder(Order order);
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}
